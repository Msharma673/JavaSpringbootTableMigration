-- Add country column to customers table
ALTER TABLE customers 
ADD COLUMN country VARCHAR(50) DEFAULT 'USA' AFTER state;

-- Update existing records
UPDATE customers SET country = 'USA' WHERE country IS NULL;

