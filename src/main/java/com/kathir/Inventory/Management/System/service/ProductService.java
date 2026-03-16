package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.Products;
import com.kathir.Inventory.Management.System.exception.ResourceNotFoundException;
import com.kathir.Inventory.Management.System.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EmailService emailService;

    public Products saveProduct(Products product) {
        return saveProduct(product, null);
    }
    
    public Products saveProduct(Products product, Integer previousQuantity) {
        // Check if this is an update (existing product)
        boolean isUpdate = product.getId() != null;
        
        if (isUpdate && previousQuantity == null) {
            // Get previous quantity before update (only if not provided)
            Products existingProduct = productRepository.findById(product.getId()).orElse(null);
            if (existingProduct != null) {
                previousQuantity = existingProduct.getQuantity();
            }
        }
        
        if (isUpdate) {
            System.out.println("🔍 Updating product: " + product.getName());
            System.out.println("   Previous quantity: " + previousQuantity);
            System.out.println("   New quantity: " + product.getQuantity());
            System.out.println("   Reorder level: " + product.getReorderLevel());
        } else {
            System.out.println("➕ Creating new product: " + product.getName() + " with quantity: " + product.getQuantity());
        }
        
        Products savedProduct = productRepository.save(product);
        
        // Only send alert if:
        // 1. This is an UPDATE (not a new product)
        // 2. Quantity decreased
        // 3. New quantity is at or below reorder level
        if (isUpdate && previousQuantity != null && 
            savedProduct.getQuantity() < previousQuantity &&
            savedProduct.getQuantity() <= savedProduct.getReorderLevel()) {
            
            System.out.println("⚠️  LOW STOCK DETECTED - Sending email alert");
            
            try {
                String subject = "⚠️ Low Stock Alert - " + savedProduct.getName();
                String body = "Product: " + savedProduct.getName() +
                        "\nPrevious Quantity: " + previousQuantity +
                        "\nCurrent Quantity: " + savedProduct.getQuantity() +
                        "\nReorder Level: " + savedProduct.getReorderLevel() +
                        "\n\nStock has decreased below reorder level. Please restock soon.";

                emailService.sendLowStockAlert(
                        "kathirganesan11@gmail.com",
                        subject,
                        body);
                        
                System.out.println("📧 Low stock alert email sent successfully!");
            } catch (Exception e) {
                // Log error but don't fail the save operation
                System.err.println("❌ Failed to send low stock email: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (!isUpdate) {
                System.out.println("ℹ️  No email sent - new product creation");
            } else if (previousQuantity == null) {
                System.out.println("ℹ️  No email sent - previous quantity not found");
            } else if (savedProduct.getQuantity() >= previousQuantity) {
                System.out.println("ℹ️  No email sent - quantity increased or stayed same");
            } else if (savedProduct.getQuantity() > savedProduct.getReorderLevel()) {
                System.out.println("ℹ️  No email sent - still above reorder level");
            }
        }
        
        return savedProduct;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Products> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public Products updateStock(Long productId, int newQuantity) {

        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        product.setQuantity(newQuantity);
        productRepository.save(product);

        if (product.getQuantity() <= product.getReorderLevel()) {

            String subject = "Low Stock Alert ⚠";
            String body = "Product: " + product.getName() +
                    "\nAvailable Quantity: " + product.getQuantity() +
                    "\nReorder Level: " + product.getReorderLevel() +
                    "\nPlease restock soon.";

            emailService.sendLowStockAlert(
                    "admin@inventory.com",
                    subject,
                    body);
        }

        return product;
    }
}