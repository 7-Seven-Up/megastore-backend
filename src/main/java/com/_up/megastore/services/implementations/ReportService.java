package com._up.megastore.services.implementations;

import com._up.megastore.data.reports.OrdersByStateResponse;
import com._up.megastore.data.sp.StoredProcedureExecutor;
import com._up.megastore.services.interfaces.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final StoredProcedureExecutor storedProcedureExecutor;

    @Override
    public OrdersByStateResponse getOrdersByStateAndDate(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {
        return storedProcedureExecutor.executeFindOrdersByState(dateFrom, dateTo);
    }
}
