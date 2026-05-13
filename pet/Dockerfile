FROM openjdk:17-jdk-slim
WORKDIR /app
#Se asume que el jar se genera en la carpeta target tras 'mvn install'
COPY target/pet-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]