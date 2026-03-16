package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.Supplier;
import com.kathir.Inventory.Management.System.exception.ResourceNotFoundException;
import com.kathir.Inventory.Management.System.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}