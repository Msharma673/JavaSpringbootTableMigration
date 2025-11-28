package JavaSpringbootTableMigration.JavaSpringbootTableMigration.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    @Column(name = "address", length = 200)
    private String address;

    @Size(max = 50, message = "City must not exceed 50 characters")
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50, message = "State must not exceed 50 characters")
    @Column(name = "state", length = 50)
    private String state;

    @Size(max = 10, message = "Zip code must not exceed 10 characters")
    @Column(name = "zip_code", length = 10)
    private String zipCode;

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

