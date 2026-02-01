# Multi-Service Auth & Data Processing System

Система складається з двох мікросервісів: **Auth API** (Сервіс А) та **Data API** (Сервіс Б). 
Реалізовано авторизацію через JWT, міжсервісну взаємодію з перевіркою внутрішнього токена та автоматичне логування операцій у PostgreSQL.

## Технологічний стек
* **Java 17** / **Spring Boot 3.2.2**
* **PostgreSQL**
* **Spring Security + JWT**
* **Docker & Docker Compose**
* **Maven**

---

## Як запустити проект

### 1. Збірка JAR файлів
Виконайте збірку обох сервісів (тести пропускаються для швидкості збірки образу):

mvn -f auth-api/pom.xml clean package -DskipTests

mvn -f data-api/pom.xml clean package -DskipTests
  

### 2. Розгортання через Docker

Підніміть всю інфраструктуру (бази даних та сервіси) однією командою:

docker compose up -d --build

## Тестування (Run Example)

Дотримуйтесь цих кроків для перевірки повної логіки системи:

### Крок 1: Реєстрація користувача

Створіть новий обліковий запис у системі:

curl -X POST http://localhost:8080/api/auth/register 
     -H "Content-Type: application/json" 
     -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"

### Крок 2: Авторизація (Login)

Отримайте JWT-токен для доступу до захищених ресурсів:

curl -X POST http://localhost:8080/api/auth/login 
     -H "Content-Type: application/json" 
     -d "{\"email\":\"a@a.com\",\"password\":\"pass\"}"

### Крок 3: Обробка даних (Process)

Відправте запит на обробку даних через проксі-сервіс.  
Замініть `<token>` на ваш JWT:

curl -X POST http://localhost:8080/api/process 
     -H "Authorization: Bearer \<token\>" 
     -H "Content-Type: application/json"
     -d "{\"text\":\"hello\"}"

#### Очікуваний результат

{ "result": "TRANSFORMED TEXT" }
