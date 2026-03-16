package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}