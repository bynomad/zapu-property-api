provider "aws" {
  region = "us-east-1"
  shared_credentials_files = "/Users/blacksnake/.aws/credentials"
  profile = "default"
}

resource "aws_instance" "web" {
  ami           = "ami-0022f774911c1d690"
  instance_type = "t2.micro"

  tags = {
    Name = "demo"
  }
}