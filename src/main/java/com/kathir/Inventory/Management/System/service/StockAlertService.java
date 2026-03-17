package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.Products;
import com.kathir.Inventory.Management.System.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockAlertService {

    private final ProductRepository productRepository;
    private final EmailService emailService;
    private LocalDateTime lastEmailSent = null;
    @Scheduled(fixedRate = 60000)
    public void checkLowStock() {

        List<Products> lowStockProducts = productRepository.findLowStockProducts();

        if (!lowStockProducts.isEmpty()) {
            if (lastEmailSent != null &&
                    lastEmailSent.plusHours(6).isAfter(LocalDateTime.now())) {
                return;
            }

            StringBuilder body = new StringBuilder();
            body.append("Dear Inventory Team,\n\n")
                    .append("This is to inform you that the stock level for the following product(s) has dropped below the defined reorder level.\n\n")
                    .append("Product Details:\n\n");

            for (Products p : lowStockProducts) {

                int recommended = p.getReorderLevel() * 2 - p.getQuantity();

                body.append("Product Name: ").append(p.getName()).append("\n")
                        .append("Previous Quantity: ").append(p.getQuantity() + recommended - p.getReorderLevel() * 2 + p.getQuantity()) // optional fix below
                        .append("\nCurrent Quantity: ").append(p.getQuantity()).append("\n")
                        .append("Reorder Level: ").append(p.getReorderLevel()).append("\n")
                        .append("Recommended Order Quantity: ").append(recommended).append("\n")
                        .append("-----------------------------------\n");
            }

            body.append("\nSince the current quantity has fallen below the reorder level, immediate restocking is recommended to avoid potential stock shortages.\n\n")
                    .append("Please take the necessary action to replenish the inventory at the earliest.\n\n")
                    .append("Best regards,\n")
                    .append("Inventory Management System");

            String ALERT_EMAIL = "kathirganesan11@gmail.com";

            emailService.sendLowStockAlert(
                    ALERT_EMAIL,
                    "Inventory Low Stock Alert",
                    body.toString()
            );
            lastEmailSent = LocalDateTime.now();
        }
    }
}