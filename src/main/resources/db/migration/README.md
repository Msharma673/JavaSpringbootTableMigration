# Database Migrations

This directory contains all Flyway database migration scripts organized by purpose.

## Folder Structure

```
db/migration/
├── tables/     # Table creation migrations
├── seed/       # Seed/initial data migrations
└── alter/      # Column updates, additions, and table modifications
```

## Migration Execution Order

Flyway executes migrations in **version order** across all folders. The version number (V1, V2, V3, etc.) determines the execution order, not the folder.

**Important:** Version numbers must be unique across ALL folders. For example:
- `tables/V1__Create_employees_table.sql`
- `tables/V2__Create_customers_table.sql`
- `seed/V3__Seed_employees_data.sql`
- `seed/V4__Seed_customers_data.sql`
- `alter/V5__Add_status_column_to_employees.sql`

## Naming Convention

All migration files must follow Flyway naming convention:
```
V{version}__{description}.sql
```

Examples:
- `V1__Create_employees_table.sql`
- `V3__Seed_employees_data.sql`
- `V5__Add_status_column_to_employees.sql`

## Best Practices

1. **Sequential Versioning**: Always use the next available version number
2. **Descriptive Names**: Use clear, descriptive names in the migration file
3. **Idempotency**: Write migrations that can be safely re-run
4. **Test First**: Test migrations on development/staging before production
5. **One Change Per Migration**: Keep migrations focused on a single change
6. **Documentation**: Add comments explaining complex migrations

## Migration Types

### Tables (`tables/`)
- Create new database tables
- Define table structure, indexes, and constraints
- See `tables/README.md` for details

### Seed Data (`seed/`)
- Insert initial/test data
- Should run after table creation
- See `seed/README.md` for details

### Alterations (`alter/`)
- Add/modify/drop columns
- Add/modify indexes
- Modify constraints
- Any table structure changes
- See `alter/README.md` for details

## Configuration

The Flyway configuration in `application.properties` includes all three folders:
```properties
spring.flyway.locations=classpath:db/migration/tables,classpath:db/migration/seed,classpath:db/migration/alter
```

## Checking Migration Status

To check which migrations have been applied:
```bash
# Using Flyway CLI
flyway info

# Or check the flyway_schema_history table in your database
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
```

## Rollback Considerations

Flyway does not support automatic rollbacks. If you need to undo a migration:
1. Create a new migration with a higher version number
2. Write SQL to reverse the changes
3. Test thoroughly before applying

Example:
- `V10__Add_column.sql` (original)
- `V11__Remove_column.sql` (rollback)

