package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.Products;
import com.kathir.Inventory.Management.System.entity.StockLog;
import com.kathir.Inventory.Management.System.repository.ProductRepository;
import com.kathir.Inventory.Management.System.repository.StockLogRepository;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ProductRepository productRepository;
    private final StockLogRepository stockLogRepository;

    // 📦 Export Product Report
    public void exportProductsToCSV(HttpServletResponse response) throws Exception {

        List<Products> products = productRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=products_report.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());

        writer.writeNext(new String[]{"ID", "Name", "Category", "Quantity", "Price"});

        for (Products p : products) {
            writer.writeNext(new String[]{
                    p.getId().toString(),
                    p.getName(),
                    p.getCategory().getName(),
                    String.valueOf(p.getQuantity()),
                    String.valueOf(p.getPrice())
            });
        }

        writer.close();
    }

    public void exportStockLogsToCSV(HttpServletResponse response) throws Exception {

        List<StockLog> logs = stockLogRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=stock_logs_report.csv");

        CSVWriter writer = new CSVWriter(response.getWriter());

        writer.writeNext(new String[]{"Product ID", "Change Type", "Quantity", "Date"});

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (StockLog log : logs) {
            writer.writeNext(new String[]{
                    log.getProductId().toString(),
                    log.getChangeType().name(),
                    String.valueOf(log.getQuantityChanged()),
                    log.getChangeDate().format(formatter)
            });
        }

        writer.close();
    }
}