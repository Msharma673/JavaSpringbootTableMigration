package JavaSpringbootTableMigration.JavaSpringbootTableMigration.controller;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.EmployeeDTO;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.EmployeeService;
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
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Employees retrieved successfully");
        response.put("data", employees);
        response.put("count", employees.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employee retrieved successfully");
            response.put("data", employee);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employee created successfully");
            response.put("data", createdEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employee updated successfully");
            response.put("data", updatedEmployee);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

