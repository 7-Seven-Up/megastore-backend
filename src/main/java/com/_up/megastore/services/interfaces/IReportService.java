package com._up.megastore.services.interfaces;

import com._up.megastore.data.reports.OrdersByStateReportResponse;

import java.time.LocalDate;

public interface IReportService {
    OrdersByStateReportResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
