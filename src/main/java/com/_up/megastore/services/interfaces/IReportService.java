package com._up.megastore.services.interfaces;

import com._up.megastore.data.reports.OrdersByStateReportResponse;
import com._up.megastore.data.reports.SoldProductResponse;

import java.time.LocalDate;
import java.util.List;

public interface IReportService {
    OrdersByStateReportResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    );

    List<SoldProductResponse> getMostSoldProductsByDate(
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
