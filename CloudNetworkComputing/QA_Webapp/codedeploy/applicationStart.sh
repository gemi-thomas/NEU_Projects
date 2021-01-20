#!/bin/sh
cd /home/ubuntu/webapp
npm install
forever start server.js
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/home/ubuntu/webapp/codedeploy/cloudwatch-config.json \
    -s