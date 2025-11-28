INSERT INTO customers (first_name, last_name, email, phone, address, city, state, zip_code, created_at, updated_at) VALUES
('Alice', 'Anderson', 'alice.anderson@example.com', '9876543210', '123 Main St', 'New York', 'NY', '10001', NOW(), NOW()),
('Bob', 'Taylor', 'bob.taylor@example.com', '9876543211', '456 Oak Ave', 'Los Angeles', 'CA', '90001', NOW(), NOW()),
('Carol', 'Thomas', 'carol.thomas@example.com', '9876543212', '789 Pine Rd', 'Chicago', 'IL', '60601', NOW(), NOW()),
('Daniel', 'Jackson', 'daniel.jackson@example.com', '9876543213', '321 Elm St', 'Houston', 'TX', '77001', NOW(), NOW()),
('Emma', 'White', 'emma.white@example.com', '9876543214', '654 Maple Dr', 'Phoenix', 'AZ', '85001', NOW(), NOW()),
('Frank', 'Harris', 'frank.harris@example.com', '9876543215', '987 Cedar Ln', 'Philadelphia', 'PA', '19101', NOW(), NOW()),
('Grace', 'Martin', 'grace.martin@example.com', '9876543216', '147 Birch Way', 'San Antonio', 'TX', '78201', NOW(), NOW()),
('Henry', 'Thompson', 'henry.thompson@example.com', '9876543217', '258 Spruce Ct', 'San Diego', 'CA', '92101', NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();

