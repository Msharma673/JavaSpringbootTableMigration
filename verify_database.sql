-- Script to verify database tables and data
-- Run this in MySQL to check if tables and data exist

USE springboot_crud_db;

-- Check if tables exist
SHOW TABLES;

-- Check Flyway migration history
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

-- Check employee data
SELECT COUNT(*) as employee_count FROM employees;
SELECT * FROM employees LIMIT 5;

-- Check customer data
SELECT COUNT(*) as customer_count FROM customers;
SELECT * FROM customers LIMIT 5;

