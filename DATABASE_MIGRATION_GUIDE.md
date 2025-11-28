# Database Migration Guide - Step by Step

This guide provides complete instructions for database migrations including creating tables, updating columns, and seeding data using Flyway.

## Table of Contents

1. [Migration Folder Structure](#migration-folder-structure)
2. [Creating a New Table](#creating-a-new-table)
3. [Adding/Updating Columns](#addingupdating-columns)
4. [Seeding Data](#seeding-data)
5. [Running Migrations](#running-migrations)
6. [Checking Migration Status](#checking-migration-status)

---

## Migration Folder Structure

```
src/main/resources/db/migration/
├── tables/     # Table creation migrations
├── seed/       # Seed/initial data migrations
└── alter/      # Column updates, additions, and table modifications
```

**Important:** Flyway executes migrations in **version order** across all folders. Version numbers must be unique and sequential (V1, V2, V3, etc.).

---

## Creating a New Table

### Step 1: Create Migration File

Create a new SQL file in the `tables` folder following the naming convention:

**Naming Convention:** `V{version}__Create_{table_name}_table.sql`

**Location:** `src/main/resources/db/migration/tables/`

**Example:** `V10__Create_products_table.sql`

### Step 2: Write the SQL

```sql
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    category VARCHAR(50),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    INDEX idx_name (name),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### Step 3: Best Practices

- ✅ Use `CREATE TABLE IF NOT EXISTS` for idempotency
- ✅ Always include `id` as primary key with `AUTO_INCREMENT`
- ✅ Include `created_at` and `updated_at` timestamp columns
- ✅ Add appropriate indexes for frequently queried columns
- ✅ Use `InnoDB` engine with `utf8mb4` charset
- ✅ Set proper data types and constraints
- ✅ Add comments for complex columns

### Step 4: Run the Migration

```bash
# Option 1: Run Spring Boot application (migrations run automatically)
mvn spring-boot:run

# Option 2: Build and run JAR
mvn clean package
java -jar target/JavaSpringbootTableMigration-0.0.1-SNAPSHOT.jar
```

**Migration runs automatically on application startup.**

---

## Adding/Updating Columns

### Step 1: Create Migration File

Create a new SQL file in the `alter` folder:

**Naming Convention:** `V{version}__{description}.sql`

**Location:** `src/main/resources/db/migration/alter/`

**Example:** `V11__Add_status_column_to_products.sql`

### Step 2: Adding a New Column

```sql
-- Add status column to products table
ALTER TABLE products 
ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE' AFTER category,
ADD INDEX idx_status (status);

-- Update existing records to have ACTIVE status
UPDATE products SET status = 'ACTIVE' WHERE status IS NULL;
```

**Key Points:**
- Use `DEFAULT` value when adding `NOT NULL` columns to existing tables
- Update existing records if needed
- Add indexes for new columns if they'll be queried frequently

### Step 3: Modifying an Existing Column

```sql
-- Modify price column to support larger values
ALTER TABLE products 
MODIFY COLUMN price DECIMAL(12, 2) NOT NULL COMMENT 'Product price in USD';
```

**Example:** `V12__Modify_price_column_in_products.sql`

### Step 4: Changing Column Data Type

```sql
-- Change phone column from VARCHAR(15) to VARCHAR(20)
ALTER TABLE products 
MODIFY COLUMN phone VARCHAR(20) NOT NULL;
```

### Step 5: Adding an Index

```sql
-- Add index for better query performance
CREATE INDEX idx_price ON products(price);

-- Add composite index
CREATE INDEX idx_category_status ON products(category, status);
```

**Example:** `V13__Add_indexes_to_products.sql`

### Step 6: Dropping a Column (Use with Caution)

```sql
-- Drop unused column
ALTER TABLE products 
DROP COLUMN old_column_name;
```

**⚠️ Warning:** Always backup data before dropping columns!

### Step 7: Run the Migration

```bash
# Run Spring Boot application
mvn spring-boot:run
```

---

## Seeding Data

### Step 1: Create Seed Migration File

Create a new SQL file in the `seed` folder:

**Naming Convention:** `V{version}__Seed_{table_name}_data.sql`

**Location:** `src/main/resources/db/migration/seed/`

**Example:** `V14__Seed_products_data.sql`

**Important:** Seed migrations should have version numbers **higher** than table creation migrations.

### Step 2: Write Seed Data SQL

```sql
INSERT INTO products (name, description, price, quantity, category, created_at, updated_at) VALUES
('Laptop', 'High-performance laptop', 999.99, 50, 'Electronics', NOW(), NOW()),
('Mouse', 'Wireless mouse', 29.99, 100, 'Electronics', NOW(), NOW()),
('Keyboard', 'Mechanical keyboard', 79.99, 75, 'Electronics', NOW(), NOW()),
('Monitor', '27-inch 4K monitor', 399.99, 30, 'Electronics', NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();
```

### Step 3: Alternative: Using INSERT IGNORE

```sql
INSERT IGNORE INTO products (name, description, price, quantity, category, created_at, updated_at) VALUES
('Laptop', 'High-performance laptop', 999.99, 50, 'Electronics', NOW(), NOW()),
('Mouse', 'Wireless mouse', 29.99, 100, 'Electronics', NOW(), NOW());
```

### Step 4: Conditional Seed (Only if Table is Empty)

```sql
-- Seed only if table is empty
INSERT INTO products (name, description, price, quantity, category, created_at, updated_at)
SELECT * FROM (
    SELECT 'Laptop' as name, 'High-performance laptop' as description, 999.99 as price, 50 as quantity, 'Electronics' as category, NOW() as created_at, NOW() as updated_at
    UNION ALL
    SELECT 'Mouse', 'Wireless mouse', 29.99, 100, 'Electronics', NOW(), NOW()
) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM products);
```

### Step 5: Best Practices

- ✅ Use `ON DUPLICATE KEY UPDATE` or `INSERT IGNORE` for idempotency
- ✅ Use `NOW()` for timestamp columns
- ✅ Seed only test/development data
- ✅ Never seed production-sensitive data
- ✅ Ensure table exists before seeding (lower version number)

### Step 6: Run the Migration

```bash
mvn spring-boot:run
```

---

## Running Migrations

### Automatic Migration (Recommended)

Migrations run automatically when Spring Boot application starts:

```bash
# Using Maven
mvn spring-boot:run

# Using Gradle (if using Gradle)
./gradlew bootRun
```

### Manual Migration Check

Check if migrations are configured correctly in `application.properties`:

```properties
# Flyway Configuration
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration/tables,classpath:db/migration/seed,classpath:db/migration/alter
```

### Using Flyway CLI (Optional)

If you have Flyway CLI installed:

```bash
# Check migration status
flyway info -url=jdbc:mysql://localhost:3306/springboot_crud_db -user=root -password=Macbook@1630

# Run migrations manually
flyway migrate -url=jdbc:mysql://localhost:3306/springboot_crud_db -user=root -password=Macbook@1630
```

---

## Checking Migration Status

### Method 1: Check Database Table

Connect to your MySQL database and query:

```sql
USE springboot_crud_db;

SELECT * FROM flyway_schema_history 
ORDER BY installed_rank;
```

This shows all applied migrations with their version numbers, descriptions, and execution times.

### Method 2: Check Application Logs

When the application starts, Flyway logs show which migrations were applied:

```
Flyway Community Edition 9.x.x by Redgate
Database: jdbc:mysql://localhost:3306/springboot_crud_db (MySQL 8.0)
Successfully validated 14 migrations (execution time 00:00.123s)
Current version of schema `springboot_crud_db`: 13
Migrating schema `springboot_crud_db` to version 14 - Seed products data
Successfully applied 1 migration to schema `springboot_crud_db` (execution time 00:00.456s)
```

### Method 3: Using Flyway API (Programmatic)

You can check migration status programmatically in your application code.

---

## Common Migration Scenarios

### Scenario 1: Create Table with Seed Data

1. Create table migration: `V10__Create_products_table.sql`
2. Create seed migration: `V11__Seed_products_data.sql`
3. Run application: `mvn spring-boot:run`

### Scenario 2: Add Column to Existing Table

1. Create alter migration: `V12__Add_status_column_to_products.sql`
2. Run application: `mvn spring-boot:run`

### Scenario 3: Modify Multiple Columns

1. Create alter migration: `V13__Modify_products_columns.sql`
2. Include all changes in one file or separate files (recommended: one file per logical change)

### Scenario 4: Add Indexes for Performance

1. Create alter migration: `V14__Add_indexes_to_products.sql`
2. Run application: `mvn spring-boot:run`

---

## Troubleshooting

### Migration Failed

**Error:** `Migration checksum mismatch`

**Solution:**
```sql
-- Check and fix checksum in flyway_schema_history table
UPDATE flyway_schema_history 
SET checksum = NULL 
WHERE version = 'V10';
```

### Migration Already Applied

**Error:** `Migration V10 already applied`

**Solution:** Use next available version number (V11, V12, etc.)

### Syntax Error in SQL

**Error:** `SQL syntax error`

**Solution:**
- Check SQL syntax
- Test SQL directly in MySQL client first
- Ensure proper semicolons and quotes

### Database Connection Error

**Error:** `Unable to connect to database`

**Solution:**
- Check database credentials in `application.properties`
- Ensure MySQL server is running
- Verify database exists

---

## Version Numbering Guidelines

- Start with V1, V2, V3, etc.
- Use sequential numbers (don't skip numbers)
- Version numbers must be unique across ALL folders
- Use descriptive names after version number
- Example: `V15__Add_email_verification_to_users.sql`

---

## Best Practices Summary

1. ✅ **Always use IF NOT EXISTS** for table creation
2. ✅ **Provide default values** when adding NOT NULL columns
3. ✅ **Update existing data** after schema changes if needed
4. ✅ **Test migrations** on development database first
5. ✅ **Use descriptive names** in migration files
6. ✅ **One logical change per migration** file
7. ✅ **Version numbers must be sequential** and unique
8. ✅ **Backup database** before running migrations in production
9. ✅ **Review SQL** before committing migration files
10. ✅ **Document complex migrations** with comments

---

## Quick Reference Commands

```bash
# Run application (migrations run automatically)
mvn spring-boot:run

# Build project
mvn clean package

# Run JAR file
java -jar target/JavaSpringbootTableMigration-0.0.1-SNAPSHOT.jar

# Check MySQL connection
mysql -u root -p -h localhost springboot_crud_db

# View migration history
mysql -u root -p -e "SELECT * FROM springboot_crud_db.flyway_schema_history ORDER BY installed_rank;"
```

---

## Migration File Examples

### Complete Example: Create Table + Seed Data

**V10__Create_orders_table.sql:**
```sql
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date DATETIME NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    INDEX idx_customer_id (customer_id),
    INDEX idx_order_date (order_date),
    INDEX idx_status (status),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**V11__Seed_orders_data.sql:**
```sql
INSERT INTO orders (customer_id, order_date, total_amount, status, created_at, updated_at) VALUES
(1, NOW(), 999.99, 'COMPLETED', NOW(), NOW()),
(2, NOW(), 299.99, 'PENDING', NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();
```

---

For more details, see the README files in each migration folder:
- `src/main/resources/db/migration/README.md`
- `src/main/resources/db/migration/tables/README.md`
- `src/main/resources/db/migration/seed/README.md`
- `src/main/resources/db/migration/alter/README.md`

