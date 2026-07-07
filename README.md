[README.md](https://github.com/user-attachments/files/29173644/README.md)
# Banking Management System

A backend banking application built with Spring Boot and MySQL that simulates core real-world banking operations, including account management, secure authentication, and transaction processing.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Security Implementation](#security-implementation)
- [Business Logic](#business-logic)
- [Exception Handling](#exception-handling)
- [Validation Rules](#validation-rules)
- [Project Structure](#project-structure)
- [Installation and Setup](#installation-and-setup)
- [Future Improvements](#future-improvements)
- [Author](#author)

---

## Project Overview

The Banking Management System is a backend application developed using Spring Boot and MySQL that models the core operations of a real-world banking platform. It provides a secure, layered architecture for managing customer accounts, processing deposits and withdrawals, retrieving transaction history, and authenticating users through JWT-based security.

The system is designed to reflect how backend services are structured in production environments, following standard practices such as separation of concerns (Controller-Service-Repository pattern), DTO-based request/response handling, centralized exception handling, and database-level data integrity. It is intended as a demonstration of backend engineering fundamentals, including REST API design, relational database modeling, and authentication and authorization mechanisms.

**Project Metrics:**

| Metric | Value |
|---|---|
| REST APIs Implemented | 6 |
| Banking Operations | 6 (Account Creation, Login, Deposit, Withdrawal, Balance Inquiry, Transaction History) |
| Authentication Layer | JWT-based Authentication and Authorization |
| Validation | Request-Level Input Validation Across All Endpoints |
| Exception Handling | Centralized Global Exception Handling |
| Transaction Tracking | Persistent Transaction Recording System |

---

## Features

- **Account Creation** — Creates a new bank account directly, with validated personal and contact details.
- **User Login** — Authenticates account holders and issues a signed JWT token.
- **JWT Authentication** — Secures all protected endpoints using stateless token validation.
- **Account Number Generation** — Automatically generates a unique account number upon account creation.
- **Deposit** — Allows authenticated users to deposit funds into their account.
- **Withdrawal** — Allows authenticated users to withdraw funds, subject to balance validation rules.
- **Balance Inquiry** — Retrieves the current account balance for an authenticated user.
- **Transaction History** — Maintains and retrieves a complete log of deposit and withdrawal transactions.
- **Input Validation** — Enforces validation rules on all incoming request data.
- **Exception Handling** — Provides consistent, structured error responses across the application.
- **Secure API Access** — Restricts access to banking operations to authenticated and authorized users only.

---

## Technology Stack

| Category | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Security | JWT (JSON Web Token) Authentication |
| Build Tool | Maven |
| Version Control | Git, GitHub |

---

## System Architecture

The application follows a standard layered architecture commonly used in Spring Boot backend systems:

```
Client (Postman / Frontend)
        |
        v
  Controller Layer   --> Handles HTTP requests and responses
        |
        v
   Service Layer      --> Contains business logic and validation
        |
        v
  Repository Layer    --> Handles database operations via Spring Data JPA
        |
        v
   MySQL Database      --> Persistent data storage
```

A dedicated **Security Layer** (JWT Filter + Authentication Provider) sits in front of the Controller layer and intercepts incoming requests to validate tokens before they reach protected endpoints.

This separation of concerns ensures that each layer has a single responsibility, making the codebase easier to test, maintain, and extend.

---

## Database Design

The system uses a relational database design with the following core entities:

### Account
Stores customer account information.

| Column | Type | Description |
|---|---|---|
| id | Long | Primary key |
| accountNumber | String | Unique, system-generated account number |
| fullName | String | Account holder's name |
| email | String | Unique email address |
| phoneNumber | String | Unique phone number |
| balance | Double | Current account balance |

### Transaction
Stores a record of every deposit and withdrawal transaction.

| Column | Type | Description |
|---|---|---|
| id | Long | Primary key |
| accountId | Long | Reference to the associated account |
| accountNumber | String | Account number the transaction belongs to |
| amount | Double | Transaction amount |
| type | String | DEPOSIT or WITHDRAWAL |

---

## API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/account/create` | Create a new bank account |
| POST | `/api/account/login` | Authenticate a user and generate a JWT token |
| POST | `/api/account/deposit` | Deposit funds into the authenticated user's account |
| POST | `/api/account/withdraw` | Withdraw funds from the authenticated user's account |
| GET | `/api/account/balance` | Retrieve the current account balance |
| GET | `/api/account/transactions` | Retrieve the transaction history of the authenticated account |

> The endpoints above reflect the full implemented API surface. Account creation and login are public endpoints; all remaining endpoints require a valid JWT token in the `Authorization` header.

---

## Security Implementation

The application implements stateless authentication using JSON Web Tokens (JWT).

**JWT Token Generation**
Upon successful login, the server validates the submitted credentials and generates a signed JWT containing the user's identity and an expiration timestamp.

**JWT Validation**
A custom JWT filter intercepts every incoming request, extracts the token from the `Authorization` header, validates its signature and expiration, and sets the authentication context if the token is valid.

**Protected Endpoints**
All account-related operations (deposit, withdrawal, balance inquiry, transaction history) are protected and require a valid token. Account creation and login are the only public endpoints.

**Authentication Flow**
1. A new bank account is created through the account creation endpoint.
2. User logs in with valid credentials and receives a JWT token.
3. The client includes the token in the `Authorization: Bearer <token>` header for subsequent requests.
4. The JWT filter validates the token on each request before granting access to protected resources.

**Unauthorized Access Prevention**
Requests with missing, malformed, or expired tokens are rejected with a `401 Unauthorized` response before reaching the business logic layer, preventing unauthorized access to account data.

---

## Business Logic

The service layer enforces the following core business rules:

- **Unique phone number validation** — A new account cannot be created with a phone number that already exists in the system.
- **Unique email validation** — A new account cannot be created with an email address that already exists in the system.
- **Account existence validation** — Operations such as deposit, withdrawal, and balance inquiry verify that the referenced account exists before proceeding.
- **Minimum balance checks** — Withdrawals are validated against a configured minimum balance requirement to prevent the account from being overdrawn.
- **Withdrawal validation** — Withdrawal amounts are checked against the available balance, and invalid or excessive withdrawal requests are rejected.
- **Transaction recording** — Every successful deposit or withdrawal is recorded in the `Transaction` table.
- **Input validation** — All incoming request payloads are validated for required fields, correct formats, and acceptable value ranges before processing.

---

## Exception Handling

The application uses a centralized exception handling strategy implemented via `@ControllerAdvice` and `@ExceptionHandler`, ensuring that all errors return consistent, structured JSON responses rather than raw stack traces.

Handled exception scenarios include:

- Account not found
- Duplicate email or phone number during account creation
- Invalid login credentials
- Insufficient balance during withdrawal
- Invalid or expired JWT tokens
- Validation failures on request payloads
- Generic internal server errors

A typical error response follows this structure:

```json
{
  "timestamp": "2026-06-21T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient balance for this withdrawal",
  "path": "/api/account/withdraw"
}
```

---

## Validation Rules

Request validation is enforced using Java Bean Validation (`jakarta.validation`) annotations at the DTO level, in addition to service-layer business rule checks:

- Email must follow a valid email format and must be unique.
- Phone number must follow a valid format and must be unique.
- Password must meet minimum length and complexity requirements.
- Deposit and withdrawal amounts must be greater than zero.
- Withdrawal amount must not exceed the available balance minus the minimum balance requirement.
- Required fields (name, email, phone number, password) cannot be null or empty.

---

## Project Structure

```
src
└── main
    ├── java
    │   └── com.spring.bank.project.bank
    │       ├── Account.java
    │       ├── AccountController.java
    │       ├── AccountService.java
    │       ├── BankApplication.java
    │       ├── GlobalException.java
    │       ├── Repo.java
    │       ├── Transaction.java
    │       └── TransactionRepository.java
    │
    └── resources
        └── application.properties

pom.xml
README.md
```

**File Responsibilities**

- **Account.java** — JPA entity representing a bank account, including account number, holder details, and balance.
- **AccountController.java** — REST controller exposing endpoints for account creation, login, deposit, withdrawal, balance inquiry, and transaction history.
- **AccountService.java** — Contains the core business logic for account operations, including validation rules and transaction processing.
- **BankApplication.java** — The Spring Boot application entry point used to bootstrap and run the application.
- **GlobalException.java** — Centralized exception handler that returns consistent, structured error responses across the application.
- **Repo.java** — Spring Data JPA repository interface for performing database operations on the `Account` entity.
- **Transaction.java** — JPA entity representing a single deposit or withdrawal transaction.
- **TransactionRepository.java** — Spring Data JPA repository interface for performing database operations on the `Transaction` entity.
- **application.properties** — Configuration file containing database connection settings and JWT configuration.
- **pom.xml** — Maven project configuration file defining dependencies, plugins, and build settings.
- **README.md** — Project documentation describing the system's architecture, features, and setup instructions.

---

## Installation and Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/banking-management-system.git
   cd banking-management-system
   ```

2. **Configure the database**

   Create a MySQL database:
   ```sql
   CREATE DATABASE banking_db;
   ```

   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   jwt.secret=your_jwt_secret_key
   jwt.expiration=86400000
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**

   The application will be available at:
   ```
   http://localhost:8080
   ```

   Use a tool such as Postman to test the API endpoints listed in the [API Endpoints](#api-endpoints) section.

---

## Future Improvements

- Implement role-based access control (Admin vs. Customer).
- Add fund transfer functionality between accounts.
- Introduce pagination and filtering for transaction history.
- Add unit and integration tests using JUnit and Mockito.
- Integrate Swagger/OpenAPI documentation for the API.
- Add refresh token support for extended sessions.
- Containerize the application using Docker.
- Implement email notifications for account activity.

---

## Learning Outcomes

This project was built to strengthen and demonstrate practical understanding of the following concepts:

- Object-Oriented Programming principles applied in a real backend system
- REST API development using Spring Boot
- Spring Boot application architecture and layered design
- Relational database design and entity relationship modeling
- Data persistence using JPA and Hibernate
- Authentication and authorization using JWT
- Centralized exception handling and structured error responses
- General backend development practices, including request validation and service-layer business logic

---

## Author

**[Siddhish Patel]**
Junior Java Backend Developer

GitHub: [(https://github.com/siddhishpatel2004-ux)]
LinkedIn: [www.linkedin.com/in/siddhish-patel-9a958b37a]
Email: [siddhishpatel2004@gmail.com]

---

*This project was developed as a backend engineering exercise to demonstrate practical skills in Java, Spring Boot, and relational database design.*
