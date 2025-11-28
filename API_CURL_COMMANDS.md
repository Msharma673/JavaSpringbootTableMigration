# API cURL Commands

This document contains all cURL commands for testing the REST API endpoints.

**Base URL:** `http://localhost:8080`

---

## Employee APIs

### 1. Get All Employees

```bash
curl -X GET http://localhost:8080/api/employees \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Employees retrieved successfully",
  "data": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "phone": "1234567890",
      "department": "Engineering",
      "salary": 75000.0,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ],
  "count": 8
}
```

---

### 2. Get Employee by ID

```bash
curl -X GET http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Employee retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "department": "Engineering",
    "salary": 75000.0,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
}
```

**Error Response (404):**
```json
{
  "success": false,
  "message": "Employee not found with id: 999"
}
```

---

### 3. Create New Employee

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "9876543210",
    "department": "HR",
    "salary": 65000.0
  }'
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Employee created successfully",
  "data": {
    "id": 9,
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "phone": "9876543210",
    "department": "HR",
    "salary": 65000.0,
    "createdAt": "2024-01-15T12:00:00",
    "updatedAt": "2024-01-15T12:00:00"
  }
}
```

**Error Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Employee with email alice.johnson@example.com already exists"
}
```

---

### 4. Update Employee

```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "department": "Senior Engineering",
    "salary": 85000.0
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Employee updated successfully",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "department": "Senior Engineering",
    "salary": 85000.0,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-15T12:30:00"
  }
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Employee not found with id: 999"
}
```

---

### 5. Delete Employee

```bash
curl -X DELETE http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Employee deleted successfully"
}
```

**Error Response (404 Not Found):**
```json
{
  "success": false,
  "message": "Employee not found with id: 999"
}
```

---

## Customer APIs

### 1. Get All Customers

```bash
curl -X GET http://localhost:8080/api/customers \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Customers retrieved successfully",
  "data": [
    {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Anderson",
      "email": "alice.anderson@example.com",
      "phone": "9876543210",
      "address": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zipCode": "10001",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ],
  "count": 8
}
```

---

### 2. Get Customer by ID

```bash
curl -X GET http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Customer retrieved successfully",
  "data": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Anderson",
    "email": "alice.anderson@example.com",
    "phone": "9876543210",
    "address": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
}
```

---

### 3. Create New Customer

```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Bob",
    "lastName": "Smith",
    "email": "bob.smith@example.com",
    "phone": "5551234567",
    "address": "456 Oak Avenue",
    "city": "Los Angeles",
    "state": "CA",
    "zipCode": "90001"
  }'
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Customer created successfully",
  "data": {
    "id": 9,
    "firstName": "Bob",
    "lastName": "Smith",
    "email": "bob.smith@example.com",
    "phone": "5551234567",
    "address": "456 Oak Avenue",
    "city": "Los Angeles",
    "state": "CA",
    "zipCode": "90001",
    "createdAt": "2024-01-15T12:00:00",
    "updatedAt": "2024-01-15T12:00:00"
  }
}
```

---

### 4. Update Customer

```bash
curl -X PUT http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Anderson",
    "email": "alice.anderson@example.com",
    "phone": "9876543210",
    "address": "789 New Street",
    "city": "New York",
    "state": "NY",
    "zipCode": "10002"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "Customer updated successfully",
  "data": {
    "id": 1,
    "firstName": "Alice",
    "lastName": "Anderson",
    "email": "alice.anderson@example.com",
    "phone": "9876543210",
    "address": "789 New Street",
    "city": "New York",
    "state": "NY",
    "zipCode": "10002",
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-15T12:30:00"
  }
}
```

---

### 5. Delete Customer

```bash
curl -X DELETE http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "success": true,
  "message": "Customer deleted successfully"
}
```

---

## Validation Error Examples

### Invalid Email Format

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "invalid-email",
    "phone": "1234567890",
    "department": "IT",
    "salary": 50000.0
  }'
```

**Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email should be valid"
    }
  ]
}
```

### Missing Required Fields

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test"
  }'
```

**Response (400 Bad Request):**
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": [
    {
      "field": "lastName",
      "message": "Last name is required"
    },
    {
      "field": "email",
      "message": "Email is required"
    },
    {
      "field": "phone",
      "message": "Phone number is required"
    },
    {
      "field": "department",
      "message": "Department is required"
    },
    {
      "field": "salary",
      "message": "Salary is required"
    }
  ]
}
```

---

## Pretty Print JSON Responses

To format JSON responses for better readability, use `jq`:

```bash
curl -X GET http://localhost:8080/api/employees | jq
```

Or use Python:

```bash
curl -X GET http://localhost:8080/api/employees | python -m json.tool
```

---

## Save Response to File

```bash
curl -X GET http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -o response.json
```

---

## Verbose Output (Debugging)

```bash
curl -v -X GET http://localhost:8080/api/employees \
  -H "Content-Type: application/json"
```

---

## Notes

- Replace `localhost:8080` with your actual server address if different
- All endpoints return JSON responses
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Include `Content-Type: application/json` header for POST/PUT requests
- Validation errors return 400 Bad Request status
- Not found errors return 404 Not Found status

