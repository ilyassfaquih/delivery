package com.food.reservation.customer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "code")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "code", unique = true, nullable = false, updatable = false)
    @NotNull(message = "Customer code cannot be null")
    @Size(min = 10, max = 50, message = "Code must be between 10 and 50 characters")
    private String code;


    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    private String lastName;


    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email should be valid")
    @Size(max = 100, message = "Email length cannot exceed 100 characters")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phone;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @PrePersist
    public void generateCode() {
        if (this.code == null || this.code.isEmpty()) {
            int randomNumber = (int) (Math.random() * 10000);
            this.code = "uiid-09-" + String.format("%04d", randomNumber);
        }
    }
}