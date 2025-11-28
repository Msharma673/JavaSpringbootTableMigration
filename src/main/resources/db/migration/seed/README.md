# Seed Data Migrations

This folder contains migration scripts for inserting initial/seed data into tables.

## Naming Convention

Follow Flyway naming convention: `V{version}__Seed_{table_name}_data.sql`

Examples:
- `V3__Seed_employees_data.sql`
- `V4__Seed_customers_data.sql`
- `V5__Seed_orders_data.sql`

## Best Practices

1. **Use INSERT IGNORE or ON DUPLICATE KEY UPDATE** to prevent errors on re-runs
2. **Seed data should be test/development data** only
3. **Never seed production-sensitive data** in migrations
4. **Use NOW() or CURRENT_TIMESTAMP** for timestamp columns
5. **Version numbers must be sequential** and unique across all migration folders
6. **Seed data migrations should come AFTER table creation migrations**

## Example: Inserting Seed Data

```sql
INSERT INTO table_name (column1, column2, column3, created_at, updated_at) VALUES
('value1', 'value2', 123.45, NOW(), NOW()),
('value3', 'value4', 678.90, NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();
```

## Example: Using INSERT IGNORE

```sql
INSERT IGNORE INTO table_name (column1, column2, created_at, updated_at) VALUES
('value1', 'value2', NOW(), NOW()),
('value3', 'value4', NOW(), NOW());
```

## Important Notes

- Seed data migrations are executed in version order
- Make sure the table exists before seeding (table creation should have a lower version number)
- Consider using conditional inserts if you want to seed only when table is empty

