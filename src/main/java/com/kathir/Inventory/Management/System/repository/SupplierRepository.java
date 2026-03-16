package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}