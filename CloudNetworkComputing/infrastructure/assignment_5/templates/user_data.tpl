#!/bin/bash
echo 'RDS_HOSTNAME=${some_address}' >> /etc/environment
echo "RDS_PORT=3306" >> /etc/environment
echo "RDS_DB_NAME=csye6225" >> /etc/environment
echo "RDS_USERNAME=csye6225fall2020" >> /etc/environment
echo "RDS_PASSWORD=csye6225" >> /etc/environment
echo "S3_BUCKETNAME=webapp-gemi-thomas" >> /etc/environment
echo 'SNS_ARN=${sns_arn}' >> /etc/environment