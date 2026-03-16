package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.entity.Supplier;
import com.kathir.Inventory.Management.System.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "suppliers/list";
    }

    @GetMapping("/new")
    public String newSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/form";
    }

    @GetMapping("/edit/{id}")
    public String editSupplierForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        return "suppliers/form";
    }

    @PostMapping("/save")
    public String saveSupplier(@Valid @ModelAttribute("supplier") Supplier supplier,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "suppliers/form";
        }
        boolean isNew = supplier.getId() == null;
        supplierService.saveSupplier(supplier);
        redirectAttributes.addFlashAttribute("successMessage",
                isNew ? "Supplier added successfully!" : "Supplier updated successfully!");
        return "redirect:/suppliers";
    }

    @PostMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        supplierService.deleteSupplier(id);
        redirectAttributes.addFlashAttribute("successMessage", "Supplier deleted successfully!");
        return "redirect:/suppliers";
    }
}
