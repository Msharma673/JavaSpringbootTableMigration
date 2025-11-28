# Alter Migrations

This folder contains migration scripts for:
- Adding new columns to existing tables
- Modifying existing columns (data type, constraints, etc.)
- Dropping columns (if needed)
- Adding/removing indexes
- Adding/removing constraints
- Any other table structure modifications

## Naming Convention

Follow Flyway naming convention: `V{version}__{description}.sql`

Examples:
- `V5__Add_status_column_to_employees.sql`
- `V6__Modify_phone_column_in_customers.sql`
- `V7__Add_index_on_email.sql`
- `V8__Drop_unused_column.sql`

## Best Practices

1. **Always use IF EXISTS/IF NOT EXISTS** when possible for idempotency
2. **Provide default values** when adding NOT NULL columns to existing tables
3. **Update existing data** after adding new columns if needed
4. **Test migrations** on a copy of production data before applying
5. **Document breaking changes** in migration comments
6. **Version numbers must be sequential** and unique across all migration folders

## Example: Adding a Column

```sql
-- Add new column with default value
ALTER TABLE table_name 
ADD COLUMN new_column VARCHAR(50) DEFAULT 'default_value' AFTER existing_column;

-- Update existing records if needed
UPDATE table_name SET new_column = 'calculated_value' WHERE condition;
```

## Example: Modifying a Column

```sql
-- Modify column data type and constraints
ALTER TABLE table_name 
MODIFY COLUMN column_name VARCHAR(100) NOT NULL COMMENT 'Updated description';
```

## Example: Adding an Index

```sql
-- Add index for better query performance
CREATE INDEX idx_column_name ON table_name(column_name);

-- Add composite index
CREATE INDEX idx_composite ON table_name(column1, column2);
```

