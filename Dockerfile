FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY pom.xml mvnw .mvn/ ./

RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix mvnw \
    && chmod +x mvnw

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 9191

ENTRYPOINT ["java", "-jar", "target/starLife-0.0.1-SNAPSHOT.jar"]
