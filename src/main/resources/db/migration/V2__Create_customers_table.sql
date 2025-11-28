CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    address VARCHAR(200),
    city VARCHAR(50),
    state VARCHAR(50),
    zip_code VARCHAR(10),
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    INDEX idx_email (email),
    INDEX idx_city (city)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

