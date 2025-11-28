-- Modify phone column to support international format
ALTER TABLE employees 
MODIFY COLUMN phone VARCHAR(20) NOT NULL COMMENT 'Phone number in international format';

