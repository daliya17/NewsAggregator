# use the openjdk 17-jdk-slim image as the base image
FROM openjdk:21-jdk-slim

# set working directory
WORKDIR /app

# copy the jar file to the container
COPY target/NewsAggregator-0.0.1-SNAPSHOT.jar app.jar

# expose the port
EXPOSE 8080

# run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
