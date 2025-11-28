#!/bin/bash
# Script to reset database and run migrations properly

echo "Step 1: Resetting Flyway baseline..."
cd /Users/learning/JavaSpringbootTableMigration

# First, let's repair Flyway to reset baseline
./mvnw flyway:repair

echo ""
echo "Step 2: Dropping existing tables if they exist..."
# We'll use a SQL script to drop tables
cat > /tmp/drop_tables.sql << 'EOF'
USE springboot_crud_db;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS flyway_schema_history;
SET FOREIGN_KEY_CHECKS = 1;
EOF

echo "Step 3: Running fresh migrations..."
./mvnw flyway:migrate

echo ""
echo "Step 4: Verifying migration status..."
./mvnw flyway:info

echo ""
echo "Done! Check your database now."

