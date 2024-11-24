package com._up.megastore.data.sp;

import com._up.megastore.data.reports.OrdersByStateResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StoredProcedureExecutor {

    private final EntityManager entityManager;
    private static final String SP_FIND_ORDERS_BY_STATE = "SP_FIND_ORDERS_BY_STATE";

    public OrdersByStateResponse executeFindOrdersByState(
            LocalDate dateFrom,
            LocalDate dateTo
    ) {

        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery(SP_FIND_ORDERS_BY_STATE);

        query.registerStoredProcedureParameter("date_from_in", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("date_to_in", LocalDate.class, ParameterMode.IN);

        query.registerStoredProcedureParameter("in_progress_out", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("finished_out", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("in_delivery_out", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("delivered_out", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("cancelled_out", Integer.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("total_out", Integer.class, ParameterMode.OUT);

        query.setParameter("date_from_in", dateFrom);
        query.setParameter("date_to_in", dateTo);

        query.execute();

        return new OrdersByStateResponse(
                (Integer) query.getOutputParameterValue("in_progress_out"),
                (Integer) query.getOutputParameterValue("finished_out"),
                (Integer) query.getOutputParameterValue("in_delivery_out"),
                (Integer) query.getOutputParameterValue("delivered_out"),
                (Integer) query.getOutputParameterValue("cancelled_out"),
                (Integer) query.getOutputParameterValue("total_out")
        );
    }
}


