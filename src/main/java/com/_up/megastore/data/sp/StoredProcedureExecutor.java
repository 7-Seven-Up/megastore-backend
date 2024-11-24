package com._up.megastore.data.sp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoredProcedureExecutor {

    private final EntityManager entityManager;

    public Map<String, Object> executeStoredProcedure(
            String procedureName,
            Map<String, Object> inputParameters,
            Map<String, Class<?>> outputParameters
    ) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedureName);

        inputParameters.forEach((name, value) -> {
            query.registerStoredProcedureParameter(name, value.getClass(), ParameterMode.IN);
            query.setParameter(name, value);
        });

        outputParameters.forEach((name, type) ->
                query.registerStoredProcedureParameter(name, type, ParameterMode.OUT));

        query.execute();

        return outputParameters
                .keySet()
                .stream()
                .collect(Collectors.toMap(name ->
                        name,
                        query::getOutputParameterValue
                ));
    }
}


