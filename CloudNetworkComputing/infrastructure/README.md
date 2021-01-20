## Build AWS Resource using Terraform

## Purpose
To build a VPC

#### main.tf
 - create VPC and other components

#### variables.tf
 - used to define the variables

 #### Run the pgm:
 - terraform fmt && terraform validate
 - terraform init 
 - terraform plan
 - terraform apply

 #### AWS CLI command for importing ssl:
 aws acm import-certificate --certificate fileb://prod_webappgemi_me.crt \
 --private-key fileb://prodwebappgemi2048.key \
 --certificate-chain fileb://prod_webappgemi_me.ca-bundle \
 --region us-east-1