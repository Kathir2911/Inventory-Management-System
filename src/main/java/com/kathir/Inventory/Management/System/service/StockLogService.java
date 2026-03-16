package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.StockLog;
import com.kathir.Inventory.Management.System.repository.StockLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockLogService {

    private final StockLogRepository stockLogRepository;

    public StockLog saveLog(StockLog log) {
        return stockLogRepository.save(log);
    }

    public List<StockLog> getAllLogs() {
        return stockLogRepository.findAll();
    }

    public List<StockLog> getRecentLogs() {
        return stockLogRepository.findTop10ByOrderByChangeDateDesc();
    }
}
