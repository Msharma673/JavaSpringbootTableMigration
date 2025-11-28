-- Add status column to employees table
ALTER TABLE employees 
ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE' AFTER department,
ADD INDEX idx_status (status);

-- Update existing records to have ACTIVE status
UPDATE employees SET status = 'ACTIVE' WHERE status IS NULL;

