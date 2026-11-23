FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew && ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENV JAVA_OPTS="-Xmx350m -Xss512k"
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar --server.port=${PORT:-8080}"]