package com.kathir.Inventory.Management.System.controller;

import com.kathir.Inventory.Management.System.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String reportsPage() {
        return "reports/index";
    }

    @GetMapping("/products/csv")
    public void exportProductsCsv(HttpServletResponse response) throws Exception {
        reportService.exportProductsToCSV(response);
    }

    @GetMapping("/stock-logs/csv")
    public void exportStockLogsCsv(HttpServletResponse response) throws Exception {
        reportService.exportStockLogsToCSV(response);
    }
}
