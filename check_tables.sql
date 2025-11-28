-- Quick script to check tables and data
USE springboot_crud_db;

-- Show all tables
SHOW TABLES;

-- Check Flyway migration history
SELECT version, description, type, installed_on, success 
FROM flyway_schema_history 
ORDER BY installed_rank;

-- Count employees
SELECT 'Employees Count' as info, COUNT(*) as count FROM employees
UNION ALL
SELECT 'Customers Count' as info, COUNT(*) as count FROM customers;

-- Show sample data
SELECT 'Sample Employees' as info;
SELECT id, first_name, last_name, email, department FROM employees LIMIT 3;

SELECT 'Sample Customers' as info;
SELECT id, first_name, last_name, email, city FROM customers LIMIT 3;

