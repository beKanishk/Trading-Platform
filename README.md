# Trading Application

## Overview
This is a Spring Boot-based trading application that integrates with external APIs such as CoinGecko for cryptocurrency data. The application provides functionalities to fetch real-time cryptocurrency details, offer AI-powered chat support using Gemini, and includes user authentication.

## Features
- Fetch cryptocurrency details using the CoinGecko API.
- Store and retrieve coin data from an SQL database.
- AI-powered chat support using Gemini for trading insights.
- User authentication for secure access.
- Structured service layer with `ChatBotService` to process API responses.
- Uses Spring Boot, REST APIs, and SQL for data storage.

## Technologies Used
- **Java** (Spring Boot)
- **Spring Web** (for REST APIs)
- **Spring Data JPA** (for database interactions)
- **Spring Security** (for user authentication)
- **SQL Database** (e.g., MySQL, PostgreSQL, etc.)
- **RestTemplate** (for API requests)
- **Maven** (for project management)
- **Gemini AI** (for chat-based trading support)
- **React** (for frontend development)

## Setup Instructions

### Prerequisites
- Install Java 17 or later
- Install Maven
- Set up an SQL database (MySQL/PostgreSQL) and configure it in `application.properties`
- Obtain API keys for CoinGecko and Gemini services

### Configuration
Update `application.properties` with your database credentials and API keys:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trading_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# API Keys
gemini.api.key=your_gemini_api_key
coingecko.api.key=your_coingecko_api_key
```

### Build and Run
1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/trading-app.git
   cd trading-app
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## Screenshots
Include relevant screenshots of the application, such as:
- Login Page
  ![Screenshot 2025-04-03 224224](https://github.com/user-attachments/assets/e5d98298-9769-4fa9-a427-8c4317de7c37)

- Dashboard displaying cryptocurrency details
  ![Screenshot 2025-04-03 224300](https://github.com/user-attachments/assets/0255005f-2baf-4e96-b160-f15d315a3ac7)

- AI Chat Support Interface
  ![image](https://github.com/user-attachments/assets/f49eff5e-16cc-46b3-bd31-4f3ad7fdee55)


## Future Enhancements
- Store historical coin data in the database
- Add support for multiple fiat currencies
- Improve AI chat responses with more trading insights


