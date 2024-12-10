# my-logging-starter

Description:
Custom logging Spring Boot starter.

**How to install:**

1. Download the project.
2. Build it with Maven tool

`mvn clean install`

**How to use:**

Add dependency to your pom.xml of project where you need to add this starter:

```
<dependency>
<groupId>com.arishev</groupId>
<artifactId>logging-starter</artifactId>
<version>0.0.1-SNAPSHOT</version>
</dependency>
```

Use annotation @EndPointDataToLog to log input\output data in your RestController methods

**Available Settings:** <br>
Add setting to application.yml

```
my-logging:
    enabled: true - switch on\switch off logging
    level: INFO - log level [ERROR, WARN, INFO, DEBUG, TRACE]
```
