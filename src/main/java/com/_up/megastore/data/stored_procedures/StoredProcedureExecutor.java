package com._up.megastore.data.stored_procedures;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StoredProcedureExecutor {

    private final EntityManager entityManager;

    public List<Object[]> executeStoredProcedure(
            String procedureName,
            Map<String, Object> inputParams
    ) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(procedureName);

        inputParams.forEach((name, value) -> {
            query.registerStoredProcedureParameter(name, value.getClass(), ParameterMode.IN);
            query.setParameter(name, value);
        });

        query.execute();
        return query.getResultList();
    }

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


