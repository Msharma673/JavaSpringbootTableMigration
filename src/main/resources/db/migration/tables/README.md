# Table Creation Migrations

This folder contains migration scripts for creating database tables.

## Naming Convention

Follow Flyway naming convention: `V{version}__Create_{table_name}_table.sql`

Examples:
- `V1__Create_employees_table.sql`
- `V2__Create_customers_table.sql`
- `V3__Create_orders_table.sql`

## Best Practices

1. **Use IF NOT EXISTS** to make migrations idempotent
2. **Define primary keys** for all tables
3. **Add appropriate indexes** for frequently queried columns
4. **Set proper data types** and constraints
5. **Include created_at and updated_at** timestamp columns for audit trails
6. **Use InnoDB engine** for MySQL with proper charset (utf8mb4)
7. **Version numbers must be sequential** and unique across all migration folders

## Example Table Structure

```sql
CREATE TABLE IF NOT EXISTS table_name (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    column1 VARCHAR(50) NOT NULL,
    column2 VARCHAR(100) NOT NULL UNIQUE,
    column3 DOUBLE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    INDEX idx_column1 (column1),
    INDEX idx_column2 (column2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

