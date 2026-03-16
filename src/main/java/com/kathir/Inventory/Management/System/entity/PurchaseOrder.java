package com.kathir.Inventory.Management.System.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Supplier is required")
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @NotNull(message = "Product is required")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private Double totalPrice;

    @NotNull
    private LocalDateTime orderDate;

    private String status; // PENDING, RECEIVED, CANCELLED
}