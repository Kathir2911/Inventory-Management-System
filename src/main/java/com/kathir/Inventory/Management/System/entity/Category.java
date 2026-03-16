package com.kathir.Inventory.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 50, message = "Category name must be 3-50 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 200, message = "Description can be up to 200 characters")
    private String description;
}