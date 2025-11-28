-- Final verification script - Run this in MySQL to confirm tables and data exist

USE springboot_crud_db;

-- 1. Show all tables
SELECT '=== ALL TABLES ===' as info;
SHOW TABLES;

-- 2. Check Flyway migration history
SELECT '=== FLYWAY MIGRATION HISTORY ===' as info;
SELECT version, description, type, installed_on, success 
FROM flyway_schema_history 
ORDER BY installed_rank;

-- 3. Check table structures
SELECT '=== EMPLOYEES TABLE STRUCTURE ===' as info;
DESCRIBE employees;

SELECT '=== CUSTOMERS TABLE STRUCTURE ===' as info;
DESCRIBE customers;

-- 4. Count records
SELECT '=== RECORD COUNTS ===' as info;
SELECT 'employees' as table_name, COUNT(*) as record_count FROM employees
UNION ALL
SELECT 'customers' as table_name, COUNT(*) as record_count FROM customers;

-- 5. Show sample data
SELECT '=== SAMPLE EMPLOYEES (First 3) ===' as info;
SELECT id, first_name, last_name, email, department, salary, status 
FROM employees 
LIMIT 3;

SELECT '=== SAMPLE CUSTOMERS (First 3) ===' as info;
SELECT id, first_name, last_name, email, city, state 
FROM customers 
LIMIT 3;

