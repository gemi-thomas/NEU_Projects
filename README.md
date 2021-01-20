### Project Description

#### BikeShare Data Analysis(Big Data)
The dataset used was a Trip History Data for rental bikes in the D.C area(https://data.world/sya/capital-bikeshare-trip-data). 
This dataset helped in analysis on how usage of rental bikes in the city has grown over the years(2015 to2017), 
which quarter sees the highest usage of the ride share service, popular tourist destinations, most travelled routes etc
Technologies used for analysis were: 
- Hadoop
- Hive
- Pig 

#### Cloud & Network Computing
This project was in 2 phases. The web app to upload to AWS consists of only REST API's and was developed using NodeJS Express
- Build a Question and Answer web application(concept like Stackoverflow) where a user can post questions and it will be 
answered by other users. 
- A user can attach images, update/delete their question/answer

The Cloud platform used was to deploy web application was Amazon AWS. Some of the services used in the project are:
- CloudWatch metrics for continuous monitoring of logs, traffic, instances.
- Load Balancer to maintain a minimum EC2 instance count
- SNS trigger to AWS Lambda which in turn uses SES mail service to send email to the users.
- Dynamo DB to make sure no duplicate messages are send to user
- Code Deploy agent to deploy code.
- S3 to store images, code artifact
- RDS to store data, metadata of images etc.

Other tools used:
- Packer to build ami
- Terraform to build AWS infrastructure
- GitHub Actions was extensively used to build ami, lambda fn, code deploy

#### College MarketPlace (Web Development) 
This project is a web application where students can sell/buy, list apartments for rental.
This was built entirely using Spring MVC framework and Hibernate ORM. 
Some of the features implemented:
- Security using session management, filters, interceptors
- Pagination
- Captcha
- REST API's for differential display of content
- Filter by type/name, Sort items listed 
- Request to view, AJAX call to display user contact details
- Admin priviledges to take down content
