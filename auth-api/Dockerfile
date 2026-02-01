# Етап 1: Збірка проекту за допомогою Maven
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Копіюємо pom.xml та завантажуємо залежності
COPY pom.xml .
RUN mvn dependency:go-offline

# Копіюємо вихідний код та збираємо jar (пропускаємо тести для прискорення)
COPY src ./src
RUN mvn clean package -DskipTests

# Етап 2: Запуск додатку
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Копіюємо зібраний jar з попереднього етапу
COPY --from=build /app/target/*.jar app.jar

# Відкриваємо порт 8080 (згідно з ТЗ)
EXPOSE 8080

# Команда для запуску
ENTRYPOINT ["java", "-jar", "app.jar"]