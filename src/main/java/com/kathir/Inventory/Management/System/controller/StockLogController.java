package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.service.StockLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stock-logs")
public class StockLogController {

    private final StockLogService stockLogService;

    @GetMapping
    public String listStockLogs(Model model) {
        model.addAttribute("logs", stockLogService.getAllLogs());
        return "stock-logs/list";
    }
}
