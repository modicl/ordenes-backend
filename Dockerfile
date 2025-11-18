# Etapa 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8082

# Variables de entorno (se sobreescriben en Digital Ocean)
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV JWT_SECRET=""

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
