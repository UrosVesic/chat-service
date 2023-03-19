# Stage 1: Build the application
FROM maven:3.6.3-openjdk-17 as build
COPY src /usr/home/chat-service/src
COPY ./pom.xml /usr/home/chat-service
RUN mvn -f /usr/home/chat-service/pom.xml clean package -DskipTests

# Stage 2: Package the application
FROM openjdk:17-jdk
COPY --from=build /usr/home/chat-service/target/*.jar /chat-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/chat-service.jar"]