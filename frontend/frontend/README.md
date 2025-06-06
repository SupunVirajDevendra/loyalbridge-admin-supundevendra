# LoyalBridge Admin Panel - Frontend

## Overview
This is the frontend application for the LoyalBridge Admin Panel, built with Next.js and Material-UI. It provides a modern, responsive interface for managing users, partners, transactions, and viewing dashboard statistics.

## Tech Stack
- Next.js 14
- TypeScript
- Material-UI (MUI)
- React Query
- Axios
- React Hook Form
- Zod

## Features Implemented

### Authentication
- ✅ Login page with email validation
- ✅ OTP verification
- ✅ JWT token management
- ✅ Protected routes
- ❌ Complete RBAC UI

### User Management
- ❌ User list with search/filter
- ❌ User profile view
- ❌ User status management
- ❌ CSV export
- ❌ High-risk flagging

### Partner Management
- ❌ Partner list
- ❌ Partner details
- ❌ Integration status
- ❌ Partner-specific transactions

### Conversion Logs
- ❌ Transaction list
- ❌ Filtering options
- ❌ Pagination
- ❌ CSV export

### Dashboard
- ❌ Overview statistics
- ❌ Recent transactions
- ❌ Weekly conversion graphs
- ❌ User activity metrics

## Setup Instructions

### Prerequisites
- Node.js 18 or higher
- npm or yarn

### Environment Setup
1. Create a `.env.local` file in the root directory:
```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### Installation
1. Clone the repository
2. Navigate to the project directory
3. Install dependencies:
```bash
npm install
# or
yarn install
```

### Running the Application
```bash
npm run dev
# or
yarn dev
```
The application will be available at `http://localhost:3000`

## Project Structure
```
src/
├── components/     # Reusable UI components
├── hooks/         # Custom React hooks
├── lib/           # Utility functions and API client
├── pages/         # Next.js pages
├── styles/        # Global styles and theme
└── types/         # TypeScript type definitions
```

## TODO List
1. Complete Authentication
   - Implement RBAC UI
   - Add role-based navigation
   - Add session management

2. Implement User Management
   - Create user list page
   - Add search and filtering
   - Implement user profile
   - Add CSV export

3. Create Partner Management
   - Build partner list
   - Add partner details
   - Show integration status
   - Display partner transactions

4. Build Conversion Logs
   - Create transaction list
   - Add filtering
   - Implement pagination
   - Add CSV export

5. Develop Dashboard
   - Create overview statistics
   - Add recent transactions
   - Implement conversion graphs
   - Add user activity metrics

6. Testing
   - Add unit tests
   - Add integration tests
   - Add E2E tests

7. Documentation
   - Add component documentation
   - Create user guide
   - Add deployment guide

## Development Guidelines

### Code Style
- Use TypeScript for type safety
- Follow ESLint rules
- Use Prettier for formatting
- Write meaningful component names
- Add JSDoc comments for functions

### Component Structure
- Keep components small and focused
- Use custom hooks for logic
- Implement proper error handling
- Add loading states
- Use proper TypeScript types

### State Management
- Use React Query for server state
- Use React Context for global state
- Use local state for UI state
- Implement proper error boundaries

### API Integration
- Use Axios for API calls
- Implement proper error handling
- Add request/response interceptors
- Use React Query for caching

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Testing
- ❌ Unit tests with Jest
- ❌ Integration tests with React Testing Library
- ❌ E2E tests with Cypress
