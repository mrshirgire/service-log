FROM openjdk:8-jdk-alpine
EXPOSE 8081
ARG JAR_FILE=target/service-log-1.0.jar
ADD ${JAR_FILE} service-log.jar
ENTRYPOINT ["java", "-jar","/service-log.jar", "--logfile.location=/home/logs"]