package com._up.megastore.controllers.interfaces;

import com._up.megastore.data.reports.OrdersByStateReportResponse;
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
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    );

    @GetMapping("/most-sold-products")
    List<SoldProductResponse> getMostSoldProductsByDate(
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    );
}
