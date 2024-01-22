FROM eclipse-temurin:17-jre-focal
LABEL authors="basilkiwanuka"

ADD target/*.jar app.jar
RUN sh -c 'touch /app.jar'

ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]