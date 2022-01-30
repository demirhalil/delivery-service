FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/delivery-service.jar
COPY ${JAR_FILE} delivery-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/delivery-service.jar"]
