# LoyalBridge Admin Panel - Backend

## Overview
This is the backend service for the LoyalBridge Admin Panel, built with Spring Boot. It provides RESTful APIs for user management, partner management, transaction tracking, and authentication.

## Tech Stack
- Java 17
- Spring Boot 3.x
- PostgreSQL
- Spring Security with JWT
- Spring Data JPA
- Swagger/OpenAPI

## Features Implemented

### Authentication & Security
- ✅ JWT-based authentication
- ✅ Email domain validation (@loyalbridge.io)
- ✅ Strong password requirements
- ✅ OTP-based 2FA
- ✅ Session timeout (15 minutes)
- ❌ Complete RBAC implementation

### User Management
- ✅ User model with necessary fields
- ✅ Basic CRUD operations
- ✅ User status management
- ❌ Advanced search and filtering
- ❌ CSV export functionality
- ❌ Complete user profile management

### Partner Management
- ✅ Partner model
- ✅ Basic CRUD operations
- ❌ Partner integration status
- ❌ Partner-specific transactions
- ❌ Integration testing

### Transaction & Conversion Logs
- ✅ Transaction model
- ✅ Basic transaction service
- ❌ Advanced filtering
- ❌ Pagination
- ❌ CSV export

### Dashboard
- ✅ Basic dashboard service
- ❌ Complete statistics
- ❌ Recent transactions
- ❌ Weekly conversion graphs

## Setup Instructions

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven

### Database Setup
1. Create a PostgreSQL database named `loyalbridge`
2. Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/loyalbridge
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
```bash
mvn spring-boot:run
```
4. Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Testing
- ❌ Unit tests for services
- ❌ Integration tests
- ❌ API tests

## TODO List
1. Complete RBAC implementation
   - Implement role-based endpoints
   - Add role-based access control
   - Test role-based permissions

2. Enhance User Management
   - Implement advanced search
   - Add CSV export
   - Complete user profile features

3. Improve Partner Management
   - Add integration status
   - Implement partner-specific transactions
   - Add integration testing

4. Complete Transaction Features
   - Implement advanced filtering
   - Add pagination
   - Add CSV export

5. Enhance Dashboard
   - Add complete statistics
   - Implement recent transactions
   - Add weekly conversion graphs

6. Testing
   - Add unit tests
   - Add integration tests
   - Add API tests

7. Documentation
   - Complete API documentation
   - Add Postman collection
   - Add deployment guide

## Security Considerations
- JWT token expiration: 15 minutes
- Password requirements:
  - Minimum 12 characters
  - At least one uppercase letter
  - At least one special character
  - At least one number
- Email domain restriction: @loyalbridge.io
- OTP validity: 5 minutes

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request 