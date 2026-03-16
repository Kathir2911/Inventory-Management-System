package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.entity.Products;
import com.kathir.Inventory.Management.System.service.CategoryService;
import com.kathir.Inventory.Management.System.service.ProductService;
import com.kathir.Inventory.Management.System.service.StockAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final StockAlertService stockAlertService;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Products());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/form";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/form";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer quantity,
            @RequestParam Double price,
            @RequestParam Integer reorderLevel,
            RedirectAttributes redirectAttributes) {

        Integer finalQuantity = quantity;
        if (id != null) {
            Products existingProduct = productService.findById(id);
            finalQuantity = existingProduct.getQuantity();
        } else {
            finalQuantity = 0;
        }

        Products product = Products.builder()
                .id(id)
                .name(name)
                .category(categoryService.findById(categoryId))
                .quantity(finalQuantity)
                .price(price)
                .reorderLevel(reorderLevel)
                .lastUpdated(LocalDateTime.now())
                .build();

        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("successMessage",
                id != null ? "Product updated successfully!" : "Product added successfully!");
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/products";
    }
}
