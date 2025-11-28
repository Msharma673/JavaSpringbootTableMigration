-- Add hire_date column to employees table
ALTER TABLE employees 
ADD COLUMN hire_date DATE AFTER salary;

-- Update existing records with a default hire date
UPDATE employees SET hire_date = DATE(created_at) WHERE hire_date IS NULL;

