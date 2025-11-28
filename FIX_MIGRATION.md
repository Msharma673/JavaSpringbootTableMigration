# Fix Database Migration Issue

## Problem
Tables and data are not showing because JPA's `ddl-auto=update` was conflicting with Flyway migrations.

## Solution Applied
1. Changed `spring.jpa.hibernate.ddl-auto` from `update` to `validate` in `application.properties`
2. This allows Flyway to manage schema creation instead of JPA

## Steps to Fix

### Step 1: Stop the Application
Stop the currently running Spring Boot application.

### Step 2: Reset Database (Choose one method)

#### Method A: Drop and Recreate Database (Recommended)
Connect to MySQL and run:
```sql
DROP DATABASE IF EXISTS springboot_crud_db;
CREATE DATABASE springboot_crud_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Or use the provided script:
```bash
mysql -u root -p'Macbook@1630' < reset_database.sql
```

#### Method B: Drop Flyway History Table Only
If you want to keep the database but reset migrations:
```sql
USE springboot_crud_db;
DROP TABLE IF EXISTS flyway_schema_history;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;
```

### Step 3: Restart Application
Restart the Spring Boot application. Flyway will now:
1. Create the `employees` table (V1)
2. Create the `customers` table (V2)
3. Seed employee data (V3) - 8 records
4. Seed customer data (V4) - 8 records
5. Run any alter migrations (V5-V9)

### Step 4: Verify
Check the database:
```sql
USE springboot_crud_db;
SHOW TABLES;
SELECT COUNT(*) FROM employees;
SELECT COUNT(*) FROM customers;
SELECT * FROM flyway_schema_history;
```

Or test the API:
- GET http://localhost:8080/api/employees
- GET http://localhost:8080/api/customers

## Expected Result
- `employees` table with 8 records
- `customers` table with 8 records
- `flyway_schema_history` table showing all applied migrations

