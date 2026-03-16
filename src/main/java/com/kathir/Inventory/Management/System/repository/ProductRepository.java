package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    @Query("SELECT p FROM Products p WHERE p.quantity <= p.reorderLevel")
    List<Products> findLowStockProducts();
}