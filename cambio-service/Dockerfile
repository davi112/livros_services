FROM openjdk:17-jdk-slim
ARG JAR_FILE=05-cambio-service-1/target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
