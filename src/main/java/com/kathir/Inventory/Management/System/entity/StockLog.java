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
@Table(name = "stock_logs")
public class StockLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Change type is required")
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @NotNull(message = "Quantity changed is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantityChanged;

    @NotNull(message = "Change date is required")
    private LocalDateTime changeDate;
}
