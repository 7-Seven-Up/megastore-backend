package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.IReportController;
import com._up.megastore.data.reports.OrdersByStateReportResponse;
import com._up.megastore.services.interfaces.IReportService;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ReportController implements IReportController {

    private final IReportService reportService;

    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public OrdersByStateReportResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return reportService.getOrdersByStateAndDate(dateFrom, dateTo);
    }
}
