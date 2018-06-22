# Spring Boot App - PCF integration with AWS Services - S3, DynamoDB


### Pre-requisites

#### 1. Maven

Spring Boot is compatible with Apache Maven 3.2 or above. If you donâ€™t already have Maven installed you can follow the instructions at maven.apache.org.

#### 2. GIT Bash

On Windows, if you don't already have GIT Bash installed, download [GIT Bash here](https://git-scm.com/downloads)


## Clone Code and Run

Clone Code in a working directory using

```
$ https://github.com/mayureshkrishna/pcfaws.git
```

Once you have cloned the code, you can now run the Spring Boot REST service using

```
$ mvn clean spring-boot:run
```

Update your AWS Access keys in the application-dev.properties file.

If you want to run with a specific environment application properties, then make sure to have a property file with environment name. For e.g. application-dev.properties
Now you can run with application-dev.properties using:

```
$ mvn clean spring-boot:run -Dspring.profiles.active=dev
```

## Amazon S3

### List S3 Files and Directories

```
http(s)://<uri>/s3/resources
```

## Amazon Dynamo DB

### Initialize Database

```
http(s)://<uri>/dynamo/init
```

### Get All Customers

```
http(s)://<uri>/dynamo/customers
```

### Get Customers by Last Name

```
http(s)://<uri>/dynamo/customers/Doe
```

## Deploy to PCF

### Login to PCF

```
cf login -a <PCF API URI>
```

### Create AWS Services

```
cf create-service aws-s3 standard pcfawss3
```

```
cf create-service aws-dynamodb standard pcfawsddb
```

### Build your app and Deploy to PCF

```
mvn clean package -Dspring.profiles.active=development

cf push --no-start
```

### Bind Services and Start your App

```
cf bind-service pcfaws pcfawss3

cf bind-service pcfaws pcfawsddb

cf start pcfaws
```


