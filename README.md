# sms-service

**sms-service** is ***Springboot*** powered microservice that enable sending an SMS to different type of Service Providers.

> SMS (or Short Message Service) is text messaging service of most telephone, Internet, and mobile device system.

**SMS** nowadays is commonly associated with One-Time Password (**OTP**), to perform Two-Factor authentication on an account.    
It also used to send notification and marketing promotions.

### Features
- REST API to receive request for an SMS sending request.
- Multiple Service Providers setup to ensure 100% success rate
- Service Provider can be prioritize using weightage system.
- Database record insertion for sms logging purpose.

## Technology
sms-service uses a number of open source projects/libraries to work properly:
- [Springboot](https://spring.io/projects/spring-boot) - Stand-alone Spring based application (2.3.5.RELEASE)
- [Hibernate](https://hibernate.org/) - ORM framework between Java application and relational database (5.4.22.Final)
- [MyBatis](https://mybatis.org/mybatis-3/) - Persistence framework to perform database CRUD operation (2.1.3)
- [Spring JPA](https://spring.io/projects/spring-data-jpa) - Another example of persistence framework (2.3.5.RELEASE)
- [Log4J](https://logging.apache.org/log4j/2.x/) - Logging and tracing framework for auditing (2.13.3)
- [OkHttp](https://square.github.io/okhttp/) - HTTP client framework for sending/receiving HTTP request (4.9.0)
- [EhCache](https://www.ehcache.org/) - Application caching framework that fully compliance with javax.cache API (3.9.0)
- (Optional) [H2 Database](https://www.h2database.com/html/main.html) - Embedded/In-memory database for Java application (1.4.200)

## Usage
Download and install dependencies, and start **sms-service** server (can be executed at root folder)
```gradle
gradle bootRun
```
Request can be made by using API tools (e.g. Postman, SoapUI) or executing curl command
```sh
curl --location --request POST 'http://localhost:8080/sms-service/1.0/sms/instant' \
--header 'Content-Type: application/json' \
--data-raw '{
    "mobile": "60133532820",
    "message": "sms-service: Initial Testing",
    "reference": "4f63d435-73f9-4390-ba6c-ec3cd2d8ce42",
    "requestTime": "2020-11-12 01:17:29.723"
}'
```

### Sample Request
```json
{
    "mobile": "60133532820",
    "message": "sms-service: Initial Testing",
    "reference": "dee19508-9d50-49b5-9783-7b87f153f73a",
    "requestTime": "2020-11-12 01:17:29.723"
}
```
### Sample Response
```json
{
    "statusCode": "00",
    "data": "Skipped real sending",
    "message": "Success",
    "serviceProvider": "Amazon SNS",
    "sentTime": "2020-11-12 01:17:30.087"
}
```

## SMS Service Providers
Existing implementation in **sms-service** already include 4 types of service provider as example.

##### 1. Alibaba Cloud SMS (Alicloud)
Configure Alicloud properties inside ***application.properties*** in folder _sms-service/sms-app/src/main/resources_
```properties
# Alicloud properties
sms.alicloud.accessId=Abcd1234
sms.alicloud.secret=Efgh5678
sms.alicloud.region=ap-southeast-1
sms.alicloud.domain=dysmsapi.ap-southeast-1.aliyuncs.com
sms.alicloud.version=2018-05-01
sms.alicloud.action=SendMessageToGlobe
```

##### 2. Google Twilio (GCP)
Configure Twilio properties inside ***application.properties***
```properties
# GCP properties
sms.gcp.twilio.sid=AC1234
sms.gcp.twilio.token=abcd1234
sms.gcp.twilio.from=sms-service
```

##### 3. Amazon Web Service SNS (AWS)
Configure AWS credentials and region as per [Set up AWS credentials and region](https://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/setup-credentials.html).  
Configure AWS properties for sender ID inside ***application.properties***
```properties
# AWS properties
sms.aws.senderId=sms-service
```

##### 4. HTTP request API-based Service Provider (e.g. SP1)
Configure necessary properties inside ***application.properties***. Add additional properties as needed.
```properties
# SP 1 properties
sms.sp1.endpoint=http://127.0.0.1/send/sms
sms.sp1.user=dev
sms.sp1.pass=abcd1234
sms.sp1.type=0
sms.sp1.from=sms-server
sms.sp1.servid=
```

## Database
**sms-service** was packaged together with in-memory H2 database, to allow stand-alone application.   
But, it also can be configured to connect with stand-alone relational database.  
Configure Spring data source properties inside ***application.properties***
```properties
# Database properties
spring.datasource.url=jdbc:h2:mem:sms_service
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa
spring.datasource.platform=h2
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=none
```

## Development
Pull request are welcome. Do contact me to learn more.

## License
MIT