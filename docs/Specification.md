# Test task specification

## Stack and Tools

1. **Database**: Postgres
2. **Additional Tools**: Redis/Elastic, etc., for specific purposes – at your discretion.
3. **Java Version**: 11
4. **Framework**: Spring Boot
5. **API Type**: REST API
6. **Build Tool**: Maven

## Database Table Structure

### Table: USER

| Key Type | Column       | Type          | Unique | Comment               |
|----------|--------------|---------------|--------|-----------------------|
| PK       | ID           | BIGINT        | True   |                       |
|          | NAME         | VARCHAR(500)  |        |                       |
|          | DATE_OF_BIRTH| DATE          |        | Format: 01.05.1993    |
|          | PASSWORD     | VARCHAR(500)  |        | Min length: 8, max 500|

### Table: ACCOUNT

| Key Type | Column       | Type          | Unique | Comment                        |
|----------|--------------|---------------|--------|--------------------------------|
| PK       | ID           | BIGINT        | True   |                                |
| FK       | USER_ID      | BIGINT        | True   | Link to USER.ID                |
|          | BALANCE      | DECIMAL       |        | Rubles + kopecks: in code – BigDecimal |

### Table: EMAIL_DATA

| Key Type | Column       | Type          | Unique | Comment            |
|----------|--------------|---------------|--------|--------------------|
| PK       | ID           | BIGINT        | True   |                    |
| FK       | USER_ID      | BIGINT        |        | Link to USER.ID    |
|          | EMAIL        | VARCHAR(200)  | True   |                    |

### Table: PHONE_DATA

| Key Type | Column       | Type          | Unique | Comment            |
|----------|--------------|---------------|--------|--------------------|
| PK       | ID           | BIGINT        | True   |                    |
| FK       | USER_ID      | BIGINT        |        | Link to USER.ID    |
|          | PHONE        | VARCHAR(13)   | True   | Format: 79207865432|

## General System Requirements

1. **Architecture**: Three layers – API, service, DAO.
2. **User Assumption**: Only regular users (no admins, etc.).
3. **User Creation**: Users are created via migrations. For tests, create users directly in DAO.
4. **PHONE_DATA**: A user can have more than one PHONE_DATA (at least 1 is required).
5. **EMAIL_DATA**: A user can have more than one EMAIL_DATA (at least 1 is required).
6. **ACCOUNT**: A user must have exactly one ACCOUNT.
7. **Initial BALANCE**: Specified when creating the user.
8. **Balance Restriction**: The BALANCE in ACCOUNT cannot go negative under any operations.
9. **API Data Validation**: Input API data must be validated.

## Mandatory Features

1. **User Data Operations**: CREATE (only for specific user-related data), UPDATE operations for the user. The user can only change their own data:
    - Can delete/change/add email if it is not already taken by another user.
    - Can delete/change/add phone if it is not already taken by another user.
    - Cannot change other data.
2. **READ Operation**: Implement the READ operation for users. Create "user search" with filtering by the fields below and pagination (size, page/offset):
    - If "dateOfBirth" is passed, filter records where "date_of_birth" is greater than the value in the request.
    - If "phone" is passed, filter by exact match.
    - If "name" is passed, filter by like format '{text-from-request-param}%'
    - If "email" is passed, filter by exact match.
3. **JWT Token**: Add JWT token (required Claim is only USER_ID), token acquisition mechanism at your discretion. Implement it as simply as possible. Authentication can be by email+password or phone+password.
4. **Balance Increase**: Every 30 seconds, the BALANCE of each client increases by 10% but not more than 207% of the initial deposit.
    - Example:
        - Initial: 100, after increase: 110.
        - Initial: 110, after increase: 121.
5. **Money Transfer**: Implement the functionality for money transfer from one user to another.
    - Input: USER_ID (transfer from) – taken from the authenticated user's token Claim, USER_ID (transfer to) from the request, VALUE (transfer amount) from the request.
    - Deduct the amount from the sender and add it to the recipient.
    - Treat this operation as "banking" (highly significant), ensuring all necessary validations and thread-safety.

## Optional Features

1. **Swagger**: Add swagger (minimal configuration).
2. **Logging**: Add meaningful logging.
3. **Caching**: Add proper caching (e.g., at the API and DAO layers). Implementation at your discretion.

## Testing

- **Unit Test Coverage**: It is not necessary to cover all the code with tests.
- **Specific Tests**: Tests should cover the money transfer functionality and one API operation using testcontainers with MockMvc.