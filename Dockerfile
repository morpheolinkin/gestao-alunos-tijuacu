# Etapa de build (opcional se você usar mvn package localmente)
FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw -q -DskipTests package

# Etapa de runtime
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Perfil dev por padrão
ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]