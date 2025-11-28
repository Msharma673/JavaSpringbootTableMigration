INSERT INTO employees (first_name, last_name, email, phone, department, salary, created_at, updated_at) VALUES
('John', 'Doe', 'john.doe@example.com', '1234567890', 'Engineering', 75000.00, NOW(), NOW()),
('Jane', 'Smith', 'jane.smith@example.com', '1234567891', 'Marketing', 65000.00, NOW(), NOW()),
('Michael', 'Johnson', 'michael.johnson@example.com', '1234567892', 'Sales', 70000.00, NOW(), NOW()),
('Emily', 'Williams', 'emily.williams@example.com', '1234567893', 'HR', 60000.00, NOW(), NOW()),
('David', 'Brown', 'david.brown@example.com', '1234567894', 'Engineering', 80000.00, NOW(), NOW()),
('Sarah', 'Davis', 'sarah.davis@example.com', '1234567895', 'Finance', 72000.00, NOW(), NOW()),
('Robert', 'Miller', 'robert.miller@example.com', '1234567896', 'Operations', 68000.00, NOW(), NOW()),
('Lisa', 'Wilson', 'lisa.wilson@example.com', '1234567897', 'Marketing', 67000.00, NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();

