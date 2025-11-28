-- Script to reset database for fresh Flyway migration
-- Run this in MySQL before restarting the application

DROP DATABASE IF EXISTS springboot_crud_db;
CREATE DATABASE springboot_crud_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE springboot_crud_db;

-- Verify database is empty
SHOW TABLES;

