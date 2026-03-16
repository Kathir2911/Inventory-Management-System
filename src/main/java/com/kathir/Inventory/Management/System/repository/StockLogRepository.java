package com.kathir.Inventory.Management.System.repository;

import com.kathir.Inventory.Management.System.entity.StockLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockLogRepository extends JpaRepository<StockLog, Long> {

    List<StockLog> findTop10ByOrderByChangeDateDesc();

    List<StockLog> findByProductId(Long productId);
}