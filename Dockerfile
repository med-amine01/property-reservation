FROM openjdk:17-alpine3.14
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT ["java", "-jar", "/spring-boot-docker.jar"]
VOLUME logs
EXPOSE 8099