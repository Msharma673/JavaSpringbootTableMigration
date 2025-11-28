# API Creation Guide - Step by Step

This guide provides step-by-step instructions for creating a new REST API endpoint in this Spring Boot project.

## Project Structure

```
src/main/java/JavaSpringbootTableMigration/JavaSpringbootTableMigration/
├── controller/     # REST Controllers
├── service/        # Service Interfaces
├── service/impl/   # Service Implementations
├── dao/           # Repository Interfaces (JPA)
├── model/         # Entity Classes
└── dto/           # Data Transfer Objects
```

## Step-by-Step Process

### Step 1: Create the Entity Model

Create a new entity class in the `model` package.

**Location:** `src/main/java/.../model/YourEntity.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "your_table_name")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field name is required")
    @Size(min = 2, max = 50, message = "Field must be between 2 and 50 characters")
    @Column(name = "field_name", nullable = false, length = 50)
    private String fieldName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

**Key Points:**
- Use `@Entity` and `@Table` annotations
- Add validation annotations (`@NotBlank`, `@Size`, `@Email`, etc.)
- Include `created_at` and `updated_at` timestamps
- Use `@PrePersist` and `@PreUpdate` for automatic timestamp management

---

### Step 2: Create the DTO (Data Transfer Object)

Create a DTO class in the `dto` package for API request/response.

**Location:** `src/main/java/.../dto/YourEntityDTO.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YourEntityDTO {

    private Long id;

    @NotBlank(message = "Field name is required")
    @Size(min = 2, max = 50, message = "Field must be between 2 and 50 characters")
    private String fieldName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

**Key Points:**
- Include validation annotations for request validation
- Exclude internal fields that shouldn't be exposed
- Use Lombok annotations for boilerplate code reduction

---

### Step 3: Create the Repository Interface

Create a repository interface extending `JpaRepository` in the `dao` package.

**Location:** `src/main/java/.../dao/YourEntityRepository.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.dao;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.model.YourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YourEntityRepository extends JpaRepository<YourEntity, Long> {
    
    // Custom query methods (optional)
    Optional<YourEntity> findByFieldName(String fieldName);
    boolean existsByFieldName(String fieldName);
}
```

**Key Points:**
- Extend `JpaRepository<Entity, ID>`
- Add custom query methods if needed
- Spring automatically provides CRUD methods

---

### Step 4: Create the Service Interface

Create a service interface in the `service` package.

**Location:** `src/main/java/.../service/YourEntityService.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.service;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.YourEntityDTO;
import java.util.List;

public interface YourEntityService {
    List<YourEntityDTO> getAllEntities();
    YourEntityDTO getEntityById(Long id);
    YourEntityDTO createEntity(YourEntityDTO entityDTO);
    YourEntityDTO updateEntity(Long id, YourEntityDTO entityDTO);
    void deleteEntity(Long id);
}
```

**Key Points:**
- Define all business logic methods
- Use DTOs in the interface, not entities
- Follow RESTful naming conventions

---

### Step 5: Create the Service Implementation

Create the service implementation in the `service/impl` package.

**Location:** `src/main/java/.../service/impl/YourEntityServiceImpl.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.impl;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dao.YourEntityRepository;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.YourEntityDTO;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.model.YourEntity;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.YourEntityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class YourEntityServiceImpl implements YourEntityService {

    @Autowired
    private YourEntityRepository repository;

    @Override
    public List<YourEntityDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public YourEntityDTO getEntityById(Long id) {
        YourEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public YourEntityDTO createEntity(YourEntityDTO entityDTO) {
        // Add business logic validation here
        if (repository.existsByFieldName(entityDTO.getFieldName())) {
            throw new RuntimeException("Entity with fieldName " + entityDTO.getFieldName() + " already exists");
        }
        YourEntity entity = convertToEntity(entityDTO);
        YourEntity savedEntity = repository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public YourEntityDTO updateEntity(Long id, YourEntityDTO entityDTO) {
        YourEntity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));

        // Add business logic validation here
        existingEntity.setFieldName(entityDTO.getFieldName());
        // Update other fields...

        YourEntity updatedEntity = repository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteEntity(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }

    // Helper methods for Entity <-> DTO conversion
    private YourEntityDTO convertToDTO(YourEntity entity) {
        YourEntityDTO dto = new YourEntityDTO();
        dto.setId(entity.getId());
        dto.setFieldName(entity.getFieldName());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private YourEntity convertToEntity(YourEntityDTO dto) {
        YourEntity entity = new YourEntity();
        entity.setFieldName(dto.getFieldName());
        // Set other fields (exclude id, timestamps are auto-managed)
        return entity;
    }
}
```

**Key Points:**
- Implement all interface methods
- Add business logic and validation
- Handle exceptions appropriately
- Convert between Entity and DTO

---

### Step 6: Create the REST Controller

Create the REST controller in the `controller` package.

**Location:** `src/main/java/.../controller/YourEntityController.java`

**Example:**
```java
package JavaSpringbootTableMigration.JavaSpringbootTableMigration.controller;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.YourEntityDTO;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.YourEntityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/your-entities")
@AllArgsConstructor
public class YourEntityController {

    @Autowired
    private YourEntityService service;

    // GET /api/your-entities - Get all entities
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEntities() {
        List<YourEntityDTO> entities = service.getAllEntities();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Entities retrieved successfully");
        response.put("data", entities);
        response.put("count", entities.size());
        return ResponseEntity.ok(response);
    }

    // GET /api/your-entities/{id} - Get entity by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEntityById(@PathVariable Long id) {
        try {
            YourEntityDTO entity = service.getEntityById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entity retrieved successfully");
            response.put("data", entity);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // POST /api/your-entities - Create new entity
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEntity(@Valid @RequestBody YourEntityDTO entityDTO) {
        try {
            YourEntityDTO createdEntity = service.createEntity(entityDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entity created successfully");
            response.put("data", createdEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // PUT /api/your-entities/{id} - Update entity
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEntity(
            @PathVariable Long id,
            @Valid @RequestBody YourEntityDTO entityDTO) {
        try {
            YourEntityDTO updatedEntity = service.updateEntity(id, entityDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entity updated successfully");
            response.put("data", updatedEntity);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            HttpStatus status = e.getMessage().contains("not found") ?
                    HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
    }

    // DELETE /api/your-entities/{id} - Delete entity
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEntity(@PathVariable Long id) {
        try {
            service.deleteEntity(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Entity deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
```

**Key Points:**
- Use `@RestController` and `@RequestMapping` annotations
- Use appropriate HTTP methods (`@GetMapping`, `@PostMapping`, etc.)
- Validate request bodies with `@Valid`
- Return consistent response format
- Handle exceptions with appropriate HTTP status codes

---

### Step 7: Create Database Migration (Optional but Recommended)

If you created a new entity, create a database migration file.

**Location:** `src/main/resources/db/migration/tables/V{version}__Create_{table_name}_table.sql`

**Example:**
```sql
CREATE TABLE IF NOT EXISTS your_table_name (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    field_name VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    INDEX idx_field_name (field_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

See `DATABASE_MIGRATION_GUIDE.md` for detailed migration instructions.

---

## Summary Checklist

- [ ] Create Entity class in `model` package
- [ ] Create DTO class in `dto` package
- [ ] Create Repository interface in `dao` package
- [ ] Create Service interface in `service` package
- [ ] Create Service implementation in `service/impl` package
- [ ] Create REST Controller in `controller` package
- [ ] Create database migration file (if new table needed)
- [ ] Test the API endpoints

## Testing Your API

After creating the API, test it using:
- Postman
- cURL commands (see `API_CURL_COMMANDS.md`)
- Browser (for GET requests)
- Unit tests

## Common Patterns

### Response Format
All APIs return a consistent response format:
```json
{
  "success": true/false,
  "message": "Description message",
  "data": { ... },
  "count": 0  // Only for list endpoints
}
```

### Error Handling
- Use try-catch blocks in controllers
- Return appropriate HTTP status codes
- Provide meaningful error messages

### Validation
- Use Jakarta Validation annotations
- Validate in DTO classes
- Use `@Valid` annotation in controller methods

