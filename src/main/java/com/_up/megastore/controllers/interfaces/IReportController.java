package com._up.megastore.controllers.interfaces;

import com._up.megastore.data.reports.OrdersByStateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequestMapping("/api/v1/reports")
public interface IReportController {

    @GetMapping("/orders-by-state")
    OrdersByStateResponse getOrdersByStateAndDate(
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo
    );
}
