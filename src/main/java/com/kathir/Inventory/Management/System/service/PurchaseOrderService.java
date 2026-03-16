package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.ChangeType;
import com.kathir.Inventory.Management.System.entity.Products;
import com.kathir.Inventory.Management.System.entity.PurchaseOrder;
import com.kathir.Inventory.Management.System.entity.StockLog;
import com.kathir.Inventory.Management.System.exception.ResourceNotFoundException;
import com.kathir.Inventory.Management.System.repository.PurchaseOrderRepository;
import com.kathir.Inventory.Management.System.repository.StockLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductService productService;
    private final StockLogRepository stockLogRepository;

    @Transactional
    public PurchaseOrder saveOrder(PurchaseOrder order) {
        // Save the purchase order first
        PurchaseOrder savedOrder = purchaseOrderRepository.save(order);
        
        // If status is RECEIVED, increase product quantity
        if ("RECEIVED".equals(order.getStatus())) {
            Products product = order.getProduct();
            product.setQuantity(product.getQuantity() + order.getQuantity());
            product.setLastUpdated(LocalDateTime.now());
            productService.saveProduct(product);
            
            // Create stock log entry
            StockLog stockLog = StockLog.builder()
                    .productId(product.getId())
                    .changeType(ChangeType.ADD)
                    .quantityChanged(order.getQuantity())
                    .changeDate(LocalDateTime.now())
                    .build();
            stockLogRepository.save(stockLog);
        }
        
        return savedOrder;
    }

    @Transactional
    public PurchaseOrder updateOrder(PurchaseOrder updatedOrder) {
        // Get the existing order to check previous status
        PurchaseOrder existingOrder = findById(updatedOrder.getId());
        String oldStatus = existingOrder.getStatus();
        String newStatus = updatedOrder.getStatus();
        
        Products product = updatedOrder.getProduct();
        
        // Handle status transitions
        if (!oldStatus.equals(newStatus)) {
            // Case 1: Changed FROM RECEIVED to something else (decrease stock)
            if ("RECEIVED".equals(oldStatus) && !"RECEIVED".equals(newStatus)) {
                product.setQuantity(product.getQuantity() - updatedOrder.getQuantity());
                product.setLastUpdated(LocalDateTime.now());
                productService.saveProduct(product);
                
                StockLog stockLog = StockLog.builder()
                        .productId(product.getId())
                        .changeType(ChangeType.REMOVE)
                        .quantityChanged(updatedOrder.getQuantity())
                        .changeDate(LocalDateTime.now())
                        .build();
                stockLogRepository.save(stockLog);
            }
            // Case 2: Changed TO RECEIVED from something else (increase stock)
            else if (!"RECEIVED".equals(oldStatus) && "RECEIVED".equals(newStatus)) {
                product.setQuantity(product.getQuantity() + updatedOrder.getQuantity());
                product.setLastUpdated(LocalDateTime.now());
                productService.saveProduct(product);
                
                StockLog stockLog = StockLog.builder()
                        .productId(product.getId())
                        .changeType(ChangeType.ADD)
                        .quantityChanged(updatedOrder.getQuantity())
                        .changeDate(LocalDateTime.now())
                        .build();
                stockLogRepository.save(stockLog);
            }
        }
        
        return purchaseOrderRepository.save(updatedOrder);
    }

    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order", id));
    }

    @Transactional
    public void deleteOrder(Long id) {
        PurchaseOrder order = findById(id);
        
        // If order was RECEIVED, decrease product quantity when deleting
        if ("RECEIVED".equals(order.getStatus())) {
            Products product = order.getProduct();
            product.setQuantity(product.getQuantity() - order.getQuantity());
            product.setLastUpdated(LocalDateTime.now());
            productService.saveProduct(product);
            
            // Create stock log entry
            StockLog stockLog = StockLog.builder()
                    .productId(product.getId())
                    .changeType(ChangeType.REMOVE)
                    .quantityChanged(order.getQuantity())
                    .changeDate(LocalDateTime.now())
                    .build();
            stockLogRepository.save(stockLog);
        }
        
        purchaseOrderRepository.deleteById(id);
    }
}