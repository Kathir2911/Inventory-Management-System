package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.entity.PurchaseOrder;
import com.kathir.Inventory.Management.System.service.ProductService;
import com.kathir.Inventory.Management.System.service.PurchaseOrderService;
import com.kathir.Inventory.Management.System.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final SupplierService supplierService;
    private final ProductService productService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", purchaseOrderService.getAllOrders());
        return "purchase-orders/list";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new PurchaseOrder());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.getAllProducts());
        return "purchase-orders/form";
    }

    @PostMapping("/save")
    public String saveOrder(@RequestParam Long supplierId,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam Double totalPrice,
            @RequestParam(defaultValue = "PENDING") String status,
            RedirectAttributes redirectAttributes) {

        PurchaseOrder order = PurchaseOrder.builder()
                .supplier(supplierService.findById(supplierId))
                .product(productService.findById(productId))
                .quantity(quantity)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .status(status)
                .build();

        purchaseOrderService.saveOrder(order);
        redirectAttributes.addFlashAttribute("successMessage", "Purchase order created successfully!");
        return "redirect:/purchase-orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", purchaseOrderService.findById(id));
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("products", productService.getAllProducts());
        return "purchase-orders/edit";
    }

    @PostMapping("/update")
    public String updateOrder(@RequestParam Long id,
            @RequestParam Long supplierId,
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam Double totalPrice,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        PurchaseOrder order = PurchaseOrder.builder()
                .id(id)
                .supplier(supplierService.findById(supplierId))
                .product(productService.findById(productId))
                .quantity(quantity)
                .totalPrice(totalPrice)
                .orderDate(LocalDateTime.now())
                .status(status)
                .build();

        purchaseOrderService.updateOrder(order);
        redirectAttributes.addFlashAttribute("successMessage", "Purchase order updated successfully!");
        return "redirect:/purchase-orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        purchaseOrderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute("successMessage", "Purchase order deleted successfully!");
        return "redirect:/purchase-orders";
    }
}
