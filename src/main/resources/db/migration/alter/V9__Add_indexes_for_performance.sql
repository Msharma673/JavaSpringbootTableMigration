-- Add additional indexes for better query performance

-- Index on employees salary for range queries
CREATE INDEX idx_salary ON employees(salary);

-- Composite index on customers location
CREATE INDEX idx_location ON customers(city, state, country);

