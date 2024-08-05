# Application Name

This is a sample application for demonstrating the implementation of a Java Spring Boot application with Postgres, Redis, and additional tools. The application includes user management, account management, and other essential features.

## Documentation

For detailed specifications and requirements, please refer to the [Specification.md](./Specification.md) file.

## Database Schema

```mermaid
erDiagram
    USER {
        bigint ID PK
        varchar(500) NAME
        date DATE_OF_BIRTH
        varchar(500) PASSWORD
    }

    ACCOUNT {
        bigint ID PK
        bigint USER_ID FK "references USER.ID"
        numeric BALANCE
        numeric INITIAL_DEPOSIT
    }

    EMAIL_DATA {
        bigint ID PK
        bigint USER_ID FK "references USER.ID"
        varchar(200) EMAIL "unique"
    }

    PHONE_DATA {
        bigint ID PK
        bigint USER_ID FK "references USER.ID"
        varchar(13) PHONE "unique"
    }

    USER ||--o{ ACCOUNT : has
    USER ||--o{ EMAIL_DATA : has
    USER ||--o{ PHONE_DATA : has