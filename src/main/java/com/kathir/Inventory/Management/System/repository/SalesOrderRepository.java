package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}