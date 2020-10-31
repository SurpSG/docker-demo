FROM openjdk:8u252-jdk

COPY build/libs/docker-demo-1.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
