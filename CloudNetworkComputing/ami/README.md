## Git-Hub Actions to trigger AMI build

## Purpose
To build a custom AMI to run the NodeJS web-application

#### Packer-build.yml
- The packer-build.yml is used to create a new AMI out of Amazon Ubuntu(18.v)
- The code downloads Packer and run Packer to build AMI

#### ami.json
 - used to install npm, nodejs and zip/unzip package on top of Ubuntu server
 
 