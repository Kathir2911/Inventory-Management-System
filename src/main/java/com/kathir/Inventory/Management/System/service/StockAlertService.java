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
            body.append("LOW STOCK ALERT\n\n");

            for (Products p : lowStockProducts) {

                int recommended = p.getReorderLevel() * 2 - p.getQuantity();

                body.append("Product: ")
                        .append(p.getName())
                        .append("\nCurrent: ")
                        .append(p.getQuantity())
                        .append("\nRecommended Order: ")
                        .append(recommended)
                        .append("\n-----------------\n");
            }

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