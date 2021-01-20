provider "aws" {
  region = var.region
}

# Create VPC
resource "aws_vpc" "VPC_1" {
  cidr_block                       = var.vpcCIDRblock
  enable_dns_hostnames             = true
  enable_dns_support               = true
  enable_classiclink_dns_support   = true
  assign_generated_ipv6_cidr_block = false
  tags = {
    Name = var.vpcName
  }
}
# create the Subnet
resource "aws_subnet" "VPC_Subnet_1" {
  vpc_id                  = aws_vpc.VPC_1.id
  cidr_block              = var.subnet1CIDRblock
  map_public_ip_on_launch = var.mapPublicIP
  availability_zone       = var.availabilityZone1
  tags = {
    Name = var.subnet_1
  }
}

resource "aws_subnet" "VPC_Subnet_2" {
  vpc_id                  = aws_vpc.VPC_1.id
  cidr_block              = var.subnet2CIDRblock
  map_public_ip_on_launch = var.mapPublicIP
  availability_zone       = var.availabilityZone2
  tags = {
    Name = var.subnet_2
  }
}

resource "aws_subnet" "VPC_Subnet_3" {
  vpc_id                  = aws_vpc.VPC_1.id
  cidr_block              = var.subnet3CIDRblock
  map_public_ip_on_launch = var.mapPublicIP
  availability_zone       = var.availabilityZone3
  tags = {
    Name = var.subnet_3
  }
}

# Create the Internet Gateway
resource "aws_internet_gateway" "My_VPC_GW" {
  vpc_id = aws_vpc.VPC_1.id
  tags = {
    Name = var.internet_gateway
  }
}


# Create the Internet Access
resource "aws_route" "My_VPC_internet_access" {
  route_table_id         = aws_vpc.VPC_1.default_route_table_id
  destination_cidr_block = var.destinationCIDRblock
  gateway_id             = aws_internet_gateway.My_VPC_GW.id
}

# Create the App Security Group
resource "aws_security_group" "App_Security_Group" {
  vpc_id = aws_vpc.VPC_1.id
  # allow ingress of port 22
  ingress {
    cidr_blocks = var.cidrBlocks
    from_port   = var.sshPort
    to_port     = var.sshPort
    protocol    = var.connection
  }
  ingress {
    cidr_blocks = [var.vpcCIDRblock]
    from_port   = var.httpPort
    to_port     = var.httpPort
    protocol    = var.connection
  }

  ingress {
    cidr_blocks = [var.vpcCIDRblock]
    from_port   = var.customTCPPort
    to_port     = var.customTCPPort
    protocol    = var.connection
  }


  # allow egress of all ports
  egress {
    from_port   = var.egressPort
    to_port     = var.egressPort
    protocol    = "-1"
    cidr_blocks = var.cidrBlocks
  }
  tags = {
    Name = var.application_security
  }
}

# Create DB the Security Group
resource "aws_security_group" "DB_Security_Group" {
  vpc_id = aws_vpc.VPC_1.id

  ingress {
    security_groups = ["${aws_security_group.App_Security_Group.id}"]
    from_port       = var.dbPort
    to_port         = var.dbPort
    protocol        = var.connection
  }

  tags = {
    Name = var.db_security
  }
}

#Create S3 instance
resource "aws_s3_bucket" "bucket" {
  bucket        = var.s3_bucketname
  acl           = var.acltype
  force_destroy = true

  lifecycle_rule {
    enabled = true

    tags = {
      "rule"      = var.tags_rule
      "autoclean" = "true"
    }

    transition {
      days          = var.transition_days
      storage_class = var.storage_class
    }

    expiration {
      days = var.expiration_days
    }
  }
  versioning {
    enabled = false
  }
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = var.sse_algo
      }
    }
  }
}

resource "aws_s3_bucket_public_access_block" "policy" {
  bucket = aws_s3_bucket.bucket.id

  block_public_acls       = true
  block_public_policy     = true
  restrict_public_buckets = true
  ignore_public_acls      = true
}

resource "aws_db_subnet_group" "custom" {
  name       = "custom"
  subnet_ids = [aws_subnet.VPC_Subnet_1.id, aws_subnet.VPC_Subnet_2.id, aws_subnet.VPC_Subnet_3.id]

  tags = {
    Name = "My DB subnet group"
  }
}

#Create RDS instance
resource "aws_db_instance" "mydb1" {
  allocated_storage          = var.allocated_storage       # gigabytes
  backup_retention_period    = var.backup_retention_period # in days
  db_subnet_group_name       = "${aws_db_subnet_group.custom.id}"
  engine                     = var.engine
  engine_version             = var.engine_version
  identifier                 = var.db_identifier
  instance_class             = var.db_instance_class
  multi_az                   = false
  name                       = var.db_name
  password                   = var.db_pswd
  port                       = var.db_port
  publicly_accessible        = false
  storage_type               = var.type
  username                   = var.db_username
  vpc_security_group_ids     = ["${aws_security_group.DB_Security_Group.id}"]
  auto_minor_version_upgrade = false
  skip_final_snapshot        = true
  storage_encrypted          = true
}

output "rds_endpoint" {
  value = "${aws_db_instance.mydb1.endpoint}"
}

output "rds_address" {
  value = "${aws_db_instance.mydb1.address}"
}

/* 
  #Create IAM Policy and Role for EC2-S3 access to bucket 'webapp'
  resource "aws_iam_role" "EC2-CSYE6225" {
    name = "EC2-CSYE6225"

    assume_role_policy = <<EOF
  {
    "Version": "2012-10-17",
    "Statement": [
      {
        "Action": "sts:AssumeRole",
        "Principal": {
          "Service": "ec2.amazonaws.com"
        },
        "Effect": "Allow",
        "Sid": ""
      }
    ]
  }
  EOF

    tags = {
      tag-key = "EC2 Role"
    }
  }
*/

#Create IAM Role for EC2-S3 access
resource "aws_iam_role" "EC2-S3-CSYE6225" {
  name = "EC2-S3-CSYE6225-Role"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF

  tags = {
    tag-key = "EC2 Role"
  }
}

#Create IAM Policy and attach to above Role for EC2-S3 access(for bucket 'webapp-gemi-thomas')
resource "aws_iam_role_policy" "WebAppS3Policy" {
  name = "WebAppS3Policy"
  role = "${aws_iam_role.EC2-S3-CSYE6225.id}"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
                "s3:*"
            ],
            "Effect": "Allow",
            "Resource": [
                "arn:aws:s3:::${var.s3_bucketname}",
                "arn:aws:s3:::${var.s3_bucketname}/*"
            ]
        }
    ]
}
EOF
}

#Create IAM Policy and attach to above Role for EC2-S3 access(for bucket 'codedeploy-webappgemi-me')
resource "aws_iam_role_policy" "CodeDeploy-EC2-S3" {
  name = "CodeDeploy-EC2-S3"
  role = "${aws_iam_role.EC2-S3-CSYE6225.id}"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
                 "s3:Get*",
                 "s3:List*"
            ],
            "Effect": "Allow",
            "Resource": [
                "arn:aws:s3:::${var.S3bucketforCodeDeploy}",
                "arn:aws:s3:::${var.S3bucketforCodeDeploy}/*"
            ]
        }
    ]
}
EOF
}

#Create IAM Policy and attach to above Role for EC2-CloudWatchAgent 
resource "aws_iam_role_policy_attachment" "EC2-CloudWatchAgent" {
  policy_arn = "arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy"
  role       = "${aws_iam_role.EC2-S3-CSYE6225.id}"
}

resource "aws_iam_instance_profile" "profile" {
  name = "profile"
  role = "${aws_iam_role.EC2-S3-CSYE6225.name}"
}

#Create IAM Policy and attach to GHActions user
resource "aws_iam_policy" "GH-Upload-To-S3" {
  name = "GH-Upload-To-S3"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
               "s3:PutObject",
                "s3:Get*",
                "s3:List*"
            ],
            "Effect": "Allow",
            "Resource": [
                "arn:aws:s3:::${var.S3bucketforCodeDeploy}",
                "arn:aws:s3:::${var.S3bucketforCodeDeploy}/*"
            ]
        }
    ]
}
EOF
}

resource "aws_iam_policy_attachment" "gh-attach" {
  name       = "gh-attachment"
  users      = ["ghactions"]
  policy_arn = aws_iam_policy.GH-Upload-To-S3.arn
}

resource "aws_iam_policy" "GHAction" {
  name = "GHAction-policy"

  policy = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "codedeploy:RegisterApplicationRevision",
                "codedeploy:GetApplicationRevision"
            ],
            "Resource": [
                "arn:aws:codedeploy:us-east-1:${var.user_account}:application:${var.deployment_app}"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "codedeploy:CreateDeployment",
                "codedeploy:GetDeployment"
            ],
            "Resource": [
                "arn:aws:codedeploy:us-east-1:${var.user_account}:deploymentgroup:${var.deployment_app}/${var.deployment_app_group}"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [
                "codedeploy:GetDeploymentConfig"
            ],
            "Resource": [
                "arn:aws:codedeploy:us-east-1:${var.user_account}:deploymentconfig:CodeDeployDefault.AllAtOnce"
            ]
        }
    ]
}
EOF
}

resource "aws_iam_policy_attachment" "gh-action" {
  name       = "test-attachment"
  users      = ["ghactions"]
  policy_arn = aws_iam_policy.GHAction.arn
}

#CODE Deploy Service Role
resource "aws_iam_role" "CodeDeployServiceRole" {
  name = "CodeDeployServiceRole"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "codedeploy.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

#CODE Deploy Role policy
resource "aws_iam_role_policy_attachment" "AWSCodeDeployRole" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole"
  role       = aws_iam_role.CodeDeployServiceRole.name
}

resource "aws_codedeploy_app" "csye6225-webapp" {
  name = var.deployment_app
}

#Create a Route53 'A' Record
data "aws_route53_zone" "selected" {
  name         = var.user
  private_zone = false
}

resource "aws_dynamodb_table" "csye6225" {
  name           = "csye6225"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }

  point_in_time_recovery {
    enabled = false
  }
}

# Create the Load Balancer Security Group
resource "aws_security_group" "LoadBalancer_Security_Group" {
  vpc_id = aws_vpc.VPC_1.id
  # allow ingress of port 22
  ingress {
    cidr_blocks = var.cidrBlocks
    from_port   = var.sshPort
    to_port     = var.sshPort
    protocol    = var.connection
  }

  ingress {
    cidr_blocks = var.cidrBlocks
    from_port   = var.httpsPort
    to_port     = var.httpsPort
    protocol    = var.connection
  }

  # allow egress of all ports
  egress {
    from_port   = var.egressPort
    to_port     = var.egressPort
    protocol    = "-1"
    cidr_blocks = var.cidrBlocks
  }
  tags = {
    Name = "LoadBalancer_Security_Group"
  }
}

#Launch Configuration
resource "aws_launch_configuration" "csye6225LaunchConfig" {
  name                        = "csye6225LaunchConfig"
  image_id                    = var.ami_build
  key_name                    = var.ssh_key_name
  instance_type               = var.storage
  iam_instance_profile        = "${aws_iam_instance_profile.profile.name}"
  security_groups             = ["${aws_security_group.App_Security_Group.id}"]
  enable_monitoring           = false
  associate_public_ip_address = true
  root_block_device {
    volume_type = var.type
    volume_size = var.vol_size
  }
  depends_on = [aws_db_instance.mydb1, aws_sns_topic.csye6225-SNS]
  user_data  = "${data.template_file.user_data.rendered}"
}

data "template_file" "user_data" {
  template = "${file("templates/user_data.tpl")}"
  vars = {
    some_address = "${aws_db_instance.mydb1.address}"
    sns_arn      = "${aws_sns_topic.csye6225-SNS.arn}"
  }
  depends_on = [aws_db_instance.mydb1, aws_sns_topic.csye6225-SNS]
}

#Target Group
resource "aws_lb_target_group" "csye6225TargetGroup" {
  name        = "csye6225TargetGroup"
  port        = 8080
  protocol    = "HTTP"
  target_type = "instance"
  vpc_id      = aws_vpc.VPC_1.id
  health_check {
    enabled  = true
    path     = "/"
    interval = 30
  }
}

#Create Load Balancer
resource "aws_lb" "csye6225LoadBalancer" {
  name               = "csye6225LoadBalancer"
  load_balancer_type = "application"
  security_groups    = ["${aws_security_group.LoadBalancer_Security_Group.id}"]
  subnets            = [aws_subnet.VPC_Subnet_1.id, aws_subnet.VPC_Subnet_2.id, aws_subnet.VPC_Subnet_3.id]
  tags = {
    Environment = "csye6225LoadBalancer"
  }
}

#Add HTTPS Listener to Elastic Load Balancer
resource "aws_lb_listener" "csye6225LBHTTPSListener" {
  load_balancer_arn = "${aws_lb.csye6225LoadBalancer.arn}"
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.aws_acm_arn

  default_action {
    type             = "forward"
    target_group_arn = "${aws_lb_target_group.csye6225TargetGroup.arn}"
  }
}

#Create Auto-Scaling Group
resource "aws_autoscaling_group" "csye6225AutoScalingGrp" {
  name                      = "csye6225AutoScalingGrp"
  max_size                  = 5
  min_size                  = 3
  health_check_grace_period = 300
  desired_capacity          = 3
  force_delete              = true
  launch_configuration      = aws_launch_configuration.csye6225LaunchConfig.name
  target_group_arns         = [aws_lb_target_group.csye6225TargetGroup.id]
  vpc_zone_identifier       = [aws_subnet.VPC_Subnet_1.id, aws_subnet.VPC_Subnet_2.id, aws_subnet.VPC_Subnet_3.id]

  tag {
    key                 = "Name"
    value               = "CSYE6225_EC2instance"
    propagate_at_launch = true
  }
}

resource "aws_autoscaling_policy" "csye6225AutoScaleUPPolicy" {
  name                   = "csye6225AutoScaleUPPolicy"
  adjustment_type        = "ChangeInCapacity"
  policy_type            = "SimpleScaling"
  cooldown               = 60
  scaling_adjustment     = 1
  autoscaling_group_name = aws_autoscaling_group.csye6225AutoScalingGrp.name
}

resource "aws_autoscaling_policy" "csye6225AutoScaleDOWNPolicy" {
  name                   = "csye6225AutoScaleDOWNPolicy"
  adjustment_type        = "ChangeInCapacity"
  policy_type            = "SimpleScaling"
  cooldown               = 60
  scaling_adjustment     = -1
  autoscaling_group_name = aws_autoscaling_group.csye6225AutoScalingGrp.name
}

#CloudWatch Alarms for Scaling UP
resource "aws_cloudwatch_metric_alarm" "csye6225CloudWatchAlarmScaleUP" {
  alarm_name          = "csye6225CloudWatchAlarmScaleUP"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = "2"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "60"
  statistic           = "Average"
  threshold           = "75"

  dimensions = {
    AutoScalingGroupName = aws_autoscaling_group.csye6225AutoScalingGrp.name
  }

  alarm_description = "This metric monitors ec2 cpu utilization"
  alarm_actions     = [aws_autoscaling_policy.csye6225AutoScaleUPPolicy.arn]
}

#CloudWatch Alarms for Scaling DOWN
resource "aws_cloudwatch_metric_alarm" "csye6225CloudWatchAlarmScaleDOWN" {
  alarm_name          = "csye6225CloudWatchAlarmScaleDOWN"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods  = "2"
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "60"
  statistic           = "Average"
  threshold           = "15"

  dimensions = {
    AutoScalingGroupName = aws_autoscaling_group.csye6225AutoScalingGrp.name
  }

  alarm_description = "This metric monitors ec2 cpu utilization"
  alarm_actions     = [aws_autoscaling_policy.csye6225AutoScaleDOWNPolicy.arn]
}

output "LB_ZoneId" {
  value = aws_lb.csye6225LoadBalancer.zone_id
}

output "LB_DNSName" {
  value = aws_lb.csye6225LoadBalancer.dns_name
}

resource "aws_route53_record" "AWSRoute53RecordA" {
  zone_id = data.aws_route53_zone.selected.zone_id
  name    = "${data.aws_route53_zone.selected.name}"
  type    = "A"
  alias {
    name                   = aws_lb.csye6225LoadBalancer.dns_name
    zone_id                = aws_lb.csye6225LoadBalancer.zone_id
    evaluate_target_health = true
  }
}

resource "aws_codedeploy_deployment_group" "csye6225-webapp-deployment" {
  app_name               = aws_codedeploy_app.csye6225-webapp.name
  deployment_group_name  = var.deployment_app_group
  service_role_arn       = aws_iam_role.CodeDeployServiceRole.arn
  deployment_config_name = "CodeDeployDefault.AllAtOnce"
  autoscaling_groups     = [aws_autoscaling_group.csye6225AutoScalingGrp.id]
  ec2_tag_set {
    ec2_tag_filter {
      key   = "Name"
      type  = "KEY_AND_VALUE"
      value = "CSYE6225_EC2instance"
    }
  }
}

#SNS Topic
resource "aws_sns_topic" "csye6225-SNS" {
  name            = "csye6225-SNS"
  delivery_policy = <<EOF
{
  "http": {
    "defaultHealthyRetryPolicy": {
      "minDelayTarget": 20,
      "maxDelayTarget": 20,
      "numRetries": 3,
      "numMaxDelayRetries": 0,
      "numNoDelayRetries": 0,
      "numMinDelayRetries": 0,
      "backoffFunction": "linear"
    },
    "disableSubscriptionOverrides": false,
    "defaultThrottlePolicy": {
      "maxReceivesPerSecond": 1
    }
  }
}
EOF
}

#LAMBDA Fn
resource "aws_iam_role" "csye6225IAMRole_lambda" {
  name = "csye6225IAMRole_lambda"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

resource "aws_lambda_function" "csye6225LambdaFn" {
  filename      = "index.zip"
  function_name = "csye6225LambdaFn"
  role          = aws_iam_role.csye6225IAMRole_lambda.arn
  handler       = "index.handler"
  runtime       = "nodejs12.x"
}
resource "aws_iam_policy" "lambda_logging" {
  name        = "lambda_logging"
  path        = "/"
  description = "IAM policy for logging from a lambda"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
        "Sid": "ReadWriteTable",
        "Effect": "Allow",
        "Action": [
            "dynamodb:BatchGetItem",
            "dynamodb:GetItem",
            "dynamodb:Query",
            "dynamodb:Scan",
            "dynamodb:BatchWriteItem",
            "dynamodb:PutItem",
            "dynamodb:UpdateItem"
        ],
        "Resource": "arn:aws:dynamodb:*:*:table/csye6225"
    },
    {
        "Sid": "GetStreamRecords",
        "Effect": "Allow",
        "Action": "dynamodb:GetRecords",
        "Resource": "arn:aws:dynamodb:*:*:table/csye6225/stream/* "
    },
    {
      "Action": [
        "ses:SendEmail",
        "ses:SendRawEmail",
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*",
      "Effect": "Allow"
    }
  ]
}
EOF
}

resource "aws_iam_role_policy_attachment" "lambda_logs" {
  role       = aws_iam_role.csye6225IAMRole_lambda.name
  policy_arn = aws_iam_policy.lambda_logging.arn
}

#Subscribe to SNS
resource "aws_sns_topic_subscription" "csye6225Lambda" {
  topic_arn = "${aws_sns_topic.csye6225-SNS.arn}"
  protocol  = "LAMBDA"
  endpoint  = "${aws_lambda_function.csye6225LambdaFn.arn}"
}

#SNS to call Lambda
resource "aws_lambda_permission" "csye6225_SNSToTriggerLambda" {
  statement_id  = "AllowExecutionFromSNS"
  action        = "lambda:InvokeFunction"
  function_name = "${aws_lambda_function.csye6225LambdaFn.arn}"
  principal     = "sns.amazonaws.com"
  source_arn    = "${aws_sns_topic.csye6225-SNS.arn}"
}

#Create IAM Policy and attach to above Role for EC2 Role
resource "aws_iam_role_policy_attachment" "EC2_SNSPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonSNSFullAccess"
  role       = "${aws_iam_role.EC2-S3-CSYE6225.id}"
}

resource "aws_iam_policy_attachment" "LambdaPolicy_gh-action" {
  name       = "Lambda-Full-Access-Policy"
  users      = ["ghactions"]
  policy_arn = "arn:aws:iam::aws:policy/AWSLambda_FullAccess"
}

