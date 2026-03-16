package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.entity.SalesOrder;
import com.kathir.Inventory.Management.System.service.ProductService;
import com.kathir.Inventory.Management.System.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sales-orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;
    private final ProductService productService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", salesOrderService.getAllOrders());
        return "sales-orders/list";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new SalesOrder());
        model.addAttribute("products", productService.getAllProducts());
        return "sales-orders/form";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam String customerName,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam Double totalPrice,
            @RequestParam String paymentMethod,
            RedirectAttributes redirectAttributes) {

        try {
            SalesOrder order = SalesOrder.builder()
                    .customerName(customerName)
                    .product(productService.findById(productId))
                    .quantity(quantity)
                    .totalPrice(totalPrice)
                    .orderDate(LocalDateTime.now())
                    .paymentMethod(paymentMethod)
                    .build();

            salesOrderService.saveOrder(order);
            redirectAttributes.addFlashAttribute("successMessage", "Sales order created successfully!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/sales-orders/new";
        }
        
        return "redirect:/sales-orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        salesOrderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("successMessage", "Sales order deleted successfully!");
        return "redirect:/sales-orders";
    }
}
