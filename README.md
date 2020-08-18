# Bank Account Toy

## How to build and run
You can build project by following command

```shell script
mvn clean package
```

You can run either by docker-compose command or by mvn command
Docker-compose command
```shell script
docker-compose up -d
```

Maven command
```shell script
mvn spring-boot:run
```

## Testing
You can use `bankaccout.http` script for testing

For actuator link
http://localhost:8090/actuator

For swagger documentation
http://localhost:8090/swagger-ui.html