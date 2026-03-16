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
@Table(name = "sales_orders")
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is required")
    private String customerName;

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

    private String paymentMethod; // CASH, CARD, UPI
}