# LoyalBridge Admin Panel

## Project Overview
The LoyalBridge Admin Panel is a full-stack web application designed for managing a loyalty management system. It provides comprehensive tools for user management, partner management, transaction tracking, and analytics.

## Project Structure
```
loyalbridge-admin/
├── adminpanel/           # Backend (Spring Boot)
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
└── frontend/            # Frontend (Next.js)
    ├── src/
    ├── package.json
    └── README.md
```

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.x
- PostgreSQL
- Spring Security with JWT
- Spring Data JPA
- Swagger/OpenAPI

### Frontend
- Next.js 14
- TypeScript
- Material-UI (MUI)
- React Query
- Axios
- React Hook Form
- Zod

## Features

### Authentication & Security
- JWT-based authentication
- Email domain validation (@loyalbridge.io)
- Strong password requirements
- OTP-based 2FA
- Session timeout (15 minutes)
- Role-based access control (RBAC)

### User Management
- User listing with search and filtering
- User profile management
- User status control (active/inactive)
- High-risk user flagging
- CSV export functionality

### Partner Management
- Partner listing and details
- Integration status tracking
- Partner-specific transaction views
- Partner performance metrics

### Conversion Logs
- Transaction history
- Advanced filtering options
- Pagination
- CSV export functionality
- Detailed transaction information

### Dashboard
- Overview statistics
- Recent transactions
- Weekly conversion graphs
- User activity metrics
- Partner performance metrics

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- PostgreSQL 12 or higher
- Maven
- npm or yarn

### Quick Start
1. Clone the repository:
```bash
git clone https://github.com/your-username/loyalbridge-admin.git
cd loyalbridge-admin
```

2. Set up the backend:
```bash
cd adminpanel
# Create and configure PostgreSQL database
# Update application.properties with your database credentials
mvn spring-boot:run
```

3. Set up the frontend:
```bash
cd frontend
npm install
# Create .env.local file with API configuration
npm run dev
```

4. Access the application:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Documentation
- [Backend Documentation](adminpanel/README.md)
- [Frontend Documentation](frontend/README.md)
- [API Documentation](http://localhost:8080/swagger-ui.html)

## Development Workflow

### Backend Development
1. Follow the [Backend README](adminpanel/README.md) for detailed setup and development instructions
2. Use the provided Postman collection for API testing
3. Follow the coding standards and best practices

### Frontend Development
1. Follow the [Frontend README](frontend/README.md) for detailed setup and development instructions
2. Use the provided component library
3. Follow the TypeScript and React best practices

## Testing
- Backend: Unit tests, integration tests, and API tests
- Frontend: Unit tests, integration tests, and E2E tests
- API: Postman collection for manual testing

## Security Considerations
- JWT token expiration: 15 minutes
- Password requirements:
  - Minimum 12 characters
  - At least one uppercase letter
  - At least one special character
  - At least one number
- Email domain restriction: @loyalbridge.io
- OTP validity: 5 minutes
- CORS configuration
- Rate limiting
- Input validation
- SQL injection prevention

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License
This project is proprietary and confidential. Unauthorized copying, distribution, or use is strictly prohibited.

## Support
For support, please contact the development team or create an issue in the repository. 
