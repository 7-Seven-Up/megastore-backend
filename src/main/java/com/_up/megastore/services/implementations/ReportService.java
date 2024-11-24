package com._up.megastore.services.implementations;

import com._up.megastore.data.reports.OrdersByStateReportResponse;
import com._up.megastore.data.stored_procedures.GetOrdersByStateExecutor;
import com._up.megastore.services.interfaces.IReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ReportService implements IReportService {

    private final GetOrdersByStateExecutor getOrdersByStateExecutor;

    @Override
    public OrdersByStateReportResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return getOrdersByStateExecutor.executeFindOrdersByState(dateFrom, dateTo);
    }
}
