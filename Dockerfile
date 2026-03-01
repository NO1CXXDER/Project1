FROM amazoncorretto:21-al2023-headless

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]