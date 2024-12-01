package com._up.megastore.controllers.interfaces;

import com._up.megastore.data.reports.OrdersByStateReportResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com._up.megastore.data.reports.SoldProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/v1/reports")
public interface IReportController {

    @GetMapping("/orders-by-state")
    OrdersByStateReportResponse getOrdersByStateAndDate(
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).of(2024, 10, 1)}") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dateTo
    );

    @GetMapping("/most-sold-products")
    List<SoldProductResponse> getMostSoldProductsByDate(
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).of(2024, 10, 1)}") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}") @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dateTo
    );
}
