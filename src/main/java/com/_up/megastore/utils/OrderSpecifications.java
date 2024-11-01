package com._up.megastore.utils;

import com._up.megastore.data.model.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.UUID;

public class OrderSpecifications {
    public static Specification<Order> withStartPeriodDate(Date startPeriodDate) {
        return (root, query, criteriaBuilder) -> {
            if (startPeriodDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startPeriodDate);
        };
    }

    public static Specification<Order> withEndPeriodDate(Date endPeriodDate) {
        return (root, query, criteriaBuilder) -> {
            if (endPeriodDate == null) {
                return criteriaBuilder.conjunction(); // No aplica filtro si no hay fecha
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("date"), endPeriodDate);
        };
    }

    public static Specification<Order> withUserId(UUID userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("userId"), userId);
        };
    }

    public static Specification<Order> withState(String state) {
        return (root, query, criteriaBuilder) -> {
            if (state == null || state.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("state"), state);
        };
    }
}
