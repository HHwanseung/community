FROM openjdk:11-jdk-slim
WORKDIR /app
COPY . .
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/*.jar"]