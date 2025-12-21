# =========================
# 1) BUILD STAGE
# =========================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos archivos de Maven primero para cachear dependencias
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw mvnw

# (Opcional) si mvnw viene con permisos raros/CRLF, lo arreglamos igual
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw || true

# Descarga dependencias (cache)
RUN mvn -B -q dependency:go-offline

# Copiamos el resto del proyecto
COPY src ./src

# Compilamos (sin tests)
RUN mvn -B clean package -DskipTests

# =========================
# 2) RUNTIME STAGE
# =========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiamos el jar generado (genérico)
COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto en la variable PORT (tu app debe leerlo)
EXPOSE 8080

# Recomendado para RAM baja
ENV JAVA_OPTS="-Xms128m -Xmx350m"

# Importante: usar sh -c para que agarre JAVA_OPTS
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
