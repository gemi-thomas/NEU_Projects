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

