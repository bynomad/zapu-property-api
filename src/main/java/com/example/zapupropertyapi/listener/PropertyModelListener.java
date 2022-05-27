package com.example.zapupropertyapi.listener;

import com.example.zapupropertyapi.model.Property;
import com.example.zapupropertyapi.service.sequence.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PropertyModelListener extends AbstractMongoEventListener<Property> {

    private final SequenceGeneratorService sequenceGeneratorService;

    public PropertyModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Property> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Property.SEQUENCE_NAME));
        }
    }
}
