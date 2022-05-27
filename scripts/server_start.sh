#!/usr/bin/env bash
cd /home/ec2-user/server
sudo docker-compose up -d
sudo java -jar -Dserver.port=80 \
    *.jar > /dev/null 2> /dev/null < /dev/null &
cd /home/ec2-user/server