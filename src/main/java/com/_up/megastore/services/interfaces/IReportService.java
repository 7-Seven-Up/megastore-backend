package com._up.megastore.services.interfaces;

import com._up.megastore.data.reports.OrdersByStateResponse;

import java.time.LocalDate;

public interface IReportService {
    OrdersByStateResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
