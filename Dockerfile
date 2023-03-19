FROM openjdk:latest
WORKDIR /app
COPY . /app

EXPOSE 8080


COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src



CMD ["./mvnw", "spring-boot:run"]