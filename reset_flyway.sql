-- Script to reset Flyway and prepare for fresh migration
USE springboot_crud_db;

-- Drop Flyway history table to reset migration tracking
DROP TABLE IF EXISTS flyway_schema_history;

-- Drop all application tables
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;

-- Verify tables are dropped
SHOW TABLES;

