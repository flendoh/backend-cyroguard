# IAM Specification

## Purpose

Identity and Access Management (IAM) handles user authentication, authorization, and user lifecycle management for CryoGuard. This bounded context provides the security layer for all other CryoGuard services.

## Data Model

### User Entity
**IMPORTANT**: Field names and types MUST match frontend mock exactly.

```typescript
interface User {
  id: string;                           // UUID
  name: string;                         // full name
  email: string;                        // unique email
  role: 'admin' | 'operator' | 'supervisor' | 'ngo';  // lowercase to match frontend
  status: 'active' | 'inactive' | 'locked';  // lowercase to match frontend
  lastLogin?: string;                   // ISO timestamp (frontend shows "Last Login")
  createdAt?: string;                  // ISO timestamp
  // password is NEVER returned in responses
}

interface LoginResponse {
  token: string;                        // JWT token
  user: {
    id: string;
    name: string;
    email: string;
    role: string;
  };
}
```

**Note**: Frontend shows "Administrators (1), Operators (3), Inactive (1)" - roles displayed with capitalize first letter but stored as lowercase in API.

### Role Enum (Internal)
| Value | Description |
|-------|-------------|
| admin | Full system access including user management |
| operator | Container and monitoring operations |
| supervisor | Oversight and reporting access |
| ngo | Read-only access to assigned resources |

## API Endpoints

### POST /auth/login

Authenticates a user and returns a JWT token.

**Frontend expects**: Login page with email/password fields

#### Scenario: Successful login
- GIVEN a user with email `operator@cryoguard.com` and password exists with status 'active'
- WHEN client submits POST /auth/login with email and password
- THEN response SHALL contain a valid JWT token
- AND response SHALL include user id, name, email, and role
- AND lastLogin SHALL be updated to current timestamp

#### Scenario: Invalid credentials
- GIVEN no user exists with email `invalid@cryoguard.com`
- WHEN client submits POST /auth/login with invalid email
- THEN response SHALL return HTTP 401 Unauthorized
- AND no token SHALL be returned

#### Scenario: Locked account
- GIVEN a user exists with email and status 'locked'
- WHEN client submits POST /auth/login with correct credentials
- THEN response SHALL return HTTP 403 Forbidden
- AND no token SHALL be returned

---

### GET /users

Returns a paginated list of users with optional search filtering.

**Frontend expects**: Users page with stats (Total Users: 4, Administrators: 1, Operators: 3, Inactive: 1)

#### Scenario: List users with pagination
- GIVEN authenticated user has ADMIN or SUPERVISOR role
- WHEN client requests GET /users?page=0&size=20
- THEN response SHALL contain array of User objects (without password)
- AND response SHALL include pagination metadata (totalElements, totalPages, currentPage)

#### Scenario: Search users by name
- GIVEN authenticated user has ADMIN or SUPERVISOR role
- WHEN client requests GET /users?search=John&page=0&size=20
- THEN response SHALL contain only users where name contains "John" (case-insensitive)
- AND email contains "John" SHALL also be matched

#### Scenario: Filter users by role
- GIVEN authenticated user has ADMIN or SUPERVISOR role
- WHEN client requests GET /users?role=operator&page=0&size=20
- THEN response SHALL contain only users with role 'operator'

#### Scenario: Filter users by status
- GIVEN authenticated user has ADMIN or SUPERVISOR role
- WHEN client requests GET /users?status=active&page=0&size=20
- THEN response SHALL contain only users with status 'active'

#### Scenario: Unauthorized access
- GIVEN authenticated user has NGO role
- WHEN client requests GET /users
- THEN response SHALL return HTTP 403 Forbidden

---

### POST /users

Creates a new user account.

**Frontend expects**: Add User modal with Full name, email, password, role (Operator/Administrator)

#### Scenario: Create user with valid data
- GIVEN authenticated user has ADMIN role
- WHEN client submits POST /users with name "Jane Doe", email "jane@cryoguard.com", password "SecurePass123", role "operator"
- THEN user SHALL be created with status 'active'
- AND createdAt and updatedAt SHALL be set to current timestamp
- AND response SHALL include the created user (without password)

#### Scenario: Create user with duplicate email
- GIVEN a user with email "existing@cryoguard.com" already exists
- WHEN client submits POST /users with email "existing@cryoguard.com"
- THEN response SHALL return HTTP 409 Conflict
- AND no user SHALL be created

#### Scenario: Create user with invalid email format
- GIVEN authenticated user has ADMIN role
- WHEN client submits POST /users with email "not-an-email"
- THEN response SHALL return HTTP 400 Bad Request
- AND error SHALL specify email format requirement

#### Scenario: Create user with weak password
- GIVEN authenticated user has ADMIN role
- WHEN client submits POST /users with password "123"
- THEN response SHALL return HTTP 400 Bad Request
- AND error SHALL specify minimum 8 characters required

---

### PUT /users/{id}

Updates an existing user's role and/or status.

**Frontend expects**: Edit action on user row

#### Scenario: Update user role
- GIVEN authenticated user has ADMIN role
- WHEN client submits PUT /users/123 with role "supervisor"
- THEN user role SHALL be updated to 'supervisor'
- AND updatedAt SHALL be set to current timestamp
- AND response SHALL include the updated user

#### Scenario: Update user status to locked
- GIVEN authenticated user has ADMIN role
- WHEN client submits PUT /users/123 with status "locked"
- THEN user status SHALL be updated to 'locked'
- AND user SHALL be prevented from logging in

#### Scenario: Update non-existent user
- GIVEN authenticated user has ADMIN role
- WHEN client submits PUT /users/999 with role "operator"
- THEN response SHALL return HTTP 404 Not Found

#### Scenario: Update user without permission
- GIVEN authenticated user has OPERATOR role
- WHEN client submits PUT /users/123 with status "active"
- THEN response SHALL return HTTP 403 Forbidden

---

### DELETE /users/{id}

Soft deletes a user account.

**Frontend expects**: Delete action on user row

#### Scenario: Soft delete user
- GIVEN authenticated user has ADMIN role
- WHEN client requests DELETE /users/123
- THEN deletedAt SHALL be set to current timestamp
- AND status SHALL be set to 'inactive'
- AND user SHALL no longer appear in GET /users results
- AND response SHALL return HTTP 204 No Content

#### Scenario: Delete non-existent user
- GIVEN authenticated user has ADMIN role
- WHEN client requests DELETE /users/999
- THEN response SHALL return HTTP 404 Not Found

## Acceptance Criteria

| ID | Criterion | Priority |
|----|-----------|----------|
| IAM-01 | User role MUST use lowercase: 'admin', 'operator', 'supervisor', 'ngo' | MUST |
| IAM-02 | User status MUST use lowercase: 'active', 'inactive', 'locked' | MUST |
| IAM-03 | Users MUST be authenticated via JWT before accessing protected endpoints | MUST |
| IAM-04 | Passwords MUST be hashed using bcrypt before storage | MUST |
| IAM-05 | Deleted users MUST be soft-deleted (set deletedAt timestamp) | MUST |
| IAM-06 | User list MUST support pagination with page and size query parameters | MUST |
| IAM-07 | User list MUST support filtering by role and status | MUST |
| IAM-08 | User list MUST support search by name or email | SHOULD |
| IAM-09 | JWT tokens MUST expire after configurable timeout (default 24h) | MUST |
| IAM-10 | Email addresses MUST be unique across all users | MUST |
| IAM-11 | User creation MUST validate email format and password minimum length | MUST |
| IAM-12 | Locked users MUST be prevented from logging in | MUST |
| IAM-13 | Password MUST NEVER be returned in any API response | MUST |
| IAM-14 | Login response MUST include JWT token and user info | MUST |