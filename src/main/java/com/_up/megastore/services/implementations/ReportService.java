package com._up.megastore.services.implementations;

import com._up.megastore.data.reports.OrdersByStateReportResponse;
import com._up.megastore.data.reports.SoldProductResponse;
import com._up.megastore.data.stored_procedures.GetMostSoldProductsExecutor;
import com._up.megastore.data.stored_procedures.GetOrdersByStateExecutor;
import com._up.megastore.services.interfaces.IReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService implements IReportService {

    private final GetOrdersByStateExecutor getOrdersByStateExecutor;
    private final GetMostSoldProductsExecutor getMostSoldProductsExecutor;

    @Override
    public OrdersByStateReportResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return getOrdersByStateExecutor.executeFindOrdersByState(dateFrom, dateTo);
    }

    @Override
    public List<SoldProductResponse> getMostSoldProductsByDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return getMostSoldProductsExecutor.executeMostSoldProducts(dateFrom, dateTo);
    }
}
