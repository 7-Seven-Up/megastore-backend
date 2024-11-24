package com._up.megastore.data.sp;

import com._up.megastore.data.reports.OrdersByStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetOrdersByStateExecutor {

    private final StoredProcedureExecutor storedProcedureExecutor;
    private static final String SP_FIND_ORDERS_BY_STATE = "SP_FIND_ORDERS_BY_STATE";

    public OrdersByStateResponse executeFindOrdersByState(LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Object> inputParams = buildInputParameters(dateFrom, dateTo);
        Map<String, Class<?>> outputParams = buildOutputParameters();

        Map<String, Object> results = storedProcedureExecutor.executeStoredProcedure(
                SP_FIND_ORDERS_BY_STATE,
                inputParams,
                outputParams
        );

        return new OrdersByStateResponse(
                (Integer) results.get("in_progress_out"),
                (Integer) results.get("finished_out"),
                (Integer) results.get("in_delivery_out"),
                (Integer) results.get("delivered_out"),
                (Integer) results.get("cancelled_out"),
                (Integer) results.get("total_out")
        );
    }

    private Map<String, Object> buildInputParameters(LocalDate dateFrom, LocalDate dateTo) {
        return new LinkedHashMap<>() {{
            put("date_from_in", dateFrom);
            put("date_to_in", dateTo);
        }};
    }

    private Map<String, Class<?>> buildOutputParameters() {
        return new LinkedHashMap<>() {{
            put("in_progress_out", Integer.class);
            put("finished_out", Integer.class);
            put("in_delivery_out", Integer.class);
            put("delivered_out", Integer.class);
            put("cancelled_out", Integer.class);
            put("total_out", Integer.class);
        }};
    }
}
