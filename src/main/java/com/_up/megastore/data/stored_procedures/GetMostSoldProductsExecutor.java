package com._up.megastore.data.stored_procedures;

import com._up.megastore.data.reports.SoldProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class GetMostSoldProductsExecutor {

    private final StoredProcedureExecutor storedProcedureExecutor;
    private static final String SP_MORE_SOLD_PRODUCTS = "SP_MORE_SOLD_PRODUCTS";

    public List<SoldProductResponse> executeMostSoldProducts(LocalDate dateFrom, LocalDate dateTo) {
        Map<String, Object> inputParams = buildInputParameters(dateFrom, dateTo);

        List<Object[]> results = storedProcedureExecutor
                .executeStoredProcedure(SP_MORE_SOLD_PRODUCTS, inputParams);

        return results
                .stream()
                .map(row ->
                        new SoldProductResponse( (String) row[0], ((BigDecimal) row[1]).intValue(), (Double) row[2] )
                )
                .toList();
    }

    private Map<String, Object> buildInputParameters(LocalDate dateFrom, LocalDate dateTo) {
        return new LinkedHashMap<>() {{
            put("date_from_in", dateFrom);
            put("date_to_in", dateTo);
        }};
    }
}
