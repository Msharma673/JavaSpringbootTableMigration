package JavaSpringbootTableMigration.JavaSpringbootTableMigration.controller;

import JavaSpringbootTableMigration.JavaSpringbootTableMigration.dto.CustomerDTO;
import JavaSpringbootTableMigration.JavaSpringbootTableMigration.service.CustomerService;
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
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Customers retrieved successfully");
        response.put("data", customers);
        response.put("count", customers.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCustomerById(@PathVariable Long id) {
        try {
            CustomerDTO customer = customerService.getCustomerById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer retrieved successfully");
            response.put("data", customer);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer created successfully");
            response.put("data", createdCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer updated successfully");
            response.put("data", updatedCustomer);
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
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Customer deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

