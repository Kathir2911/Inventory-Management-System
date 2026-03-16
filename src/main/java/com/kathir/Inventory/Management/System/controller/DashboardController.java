package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final PurchaseOrderService purchaseOrderService;
    private final SalesOrderService salesOrderService;
    private final StockLogService stockLogService;

    @GetMapping({ "/", "/dashboard" })
    public String dashboard(Model model) {
        model.addAttribute("totalProducts", productService.getAllProducts().size());
        model.addAttribute("totalCategories", categoryService.getAllCategories().size());
        model.addAttribute("totalSuppliers", supplierService.getAllSuppliers().size());
        model.addAttribute("totalPurchaseOrders", purchaseOrderService.getAllOrders().size());
        model.addAttribute("totalSalesOrders", salesOrderService.getAllOrders().size());
        var lowStockProducts = productService.getLowStockProducts();
        System.out.println("Low stock products count: " + lowStockProducts.size());
        model.addAttribute("lowStockProducts", lowStockProducts);
        model.addAttribute("lowStockCount", lowStockProducts.size());
        model.addAttribute("recentLogs", stockLogService.getRecentLogs());
        return "dashboard";
    }
}
