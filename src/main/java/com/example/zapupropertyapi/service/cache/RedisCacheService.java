package com.example.zapupropertyapi.service.cache;

import com.example.zapupropertyapi.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

@Service
public class RedisCacheService {

    private final CacheManager cacheManager;

    @Autowired
    public RedisCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Async
    public <T extends Serializable> void putValueToCache(String cacheName, Object key, T value) {
        String stringKey = key.toString();
        if (isKeyAvailable(cacheName, stringKey)) {
            T cacheValue = getValue(cacheName, stringKey);
            if (cacheValue instanceof Collection) {
                if (((Collection<?>) cacheValue).contains(value)) {
                    ((Collection<?>) cacheValue).remove(value); // tüm category ve city için tuttuğumuz listede güncelleme yapıyoruz
                    ((Collection) cacheValue).add(value);
                } else {
                    ((Collection) cacheValue).add(value);
                }
                putValue(cacheName, stringKey, cacheValue);
            } else {
                putValue(cacheName, stringKey, value);
            }
        } else {
            putValue(cacheName, stringKey, value);
        }
    }

    @Async
    public <T extends Serializable> void deleteRelatedSearchCacheKeys(String cacheName, T value) {
        Property relatedProperty = (Property) value;

        String friendlyUrlKey = prepareFriendlyUrlCacheKey(relatedProperty);
        String parameterizedUrlKey = prepareParametrizedUrlCacheKey(relatedProperty);

        if (isKeyAvailable(cacheName, friendlyUrlKey)) {
            cacheManager.getCache(cacheName).evict(friendlyUrlKey);
        }
        if (isKeyAvailable(cacheName, parameterizedUrlKey)) {
            cacheManager.getCache(cacheName).evict(parameterizedUrlKey);
        }

    }

    private String prepareFriendlyUrlCacheKey(Property relatedProperty) {
        String cityName = relatedProperty.getCity().getName();
        String categoryName = relatedProperty.getCategory().getName();

        return new StringBuilder("/").append(categoryName).append("/").append(cityName).toString();
    }

    private String prepareParametrizedUrlCacheKey(Property relatedProperty) {
        Short cityId = relatedProperty.getCity().getId();
        Short categoryId = relatedProperty.getCategory().getId();

        return new StringBuilder("category=").append(categoryId).append("&city=").append(cityId).toString();
    }

    public <T extends Serializable> T getValue(String cacheName, String key) {
        return (T) cacheManager.getCache(cacheName).get(key).get();
    }

    public boolean isKeyAvailable(String cacheName, String key) {
        return cacheManager.getCache(cacheName).get(key) != null;
    }

    private <T extends Serializable> void putValue(String cacheName, String key, T value) {
        cacheManager.getCache(cacheName).put(key, value);
    }
}
