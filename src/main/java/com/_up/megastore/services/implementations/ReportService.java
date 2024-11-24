package com._up.megastore.services.implementations;

import com._up.megastore.data.reports.OrdersByStateResponse;
import com._up.megastore.data.sp.GetOrdersByStateExecutor;
import com._up.megastore.services.interfaces.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final GetOrdersByStateExecutor getOrdersByStateExecutor;

    @Override
    public OrdersByStateResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return getOrdersByStateExecutor.executeFindOrdersByState(dateFrom, dateTo);
    }
}
