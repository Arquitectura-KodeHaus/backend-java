# ===== Build =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src

# Copiamos primero el pom para cachear dependencias
COPY demo/pom.xml demo/pom.xml
RUN mvn -q -f demo/pom.xml -DskipTests dependency:go-offline

# Copiamos el resto y construimos
COPY demo/ demo/
RUN mvn -q -f demo/pom.xml -DskipTests package

# ===== Run =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos el jar resultante (ajusta si tu artifact produce otro nombre)
COPY --from=build /src/demo/target/*-SNAPSHOT.jar /app/app.jar

# Cloud Run da $PORT; pasamos a Spring como --server.port
ENV PORT=8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT}"]
