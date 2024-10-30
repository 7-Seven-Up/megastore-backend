package com._up.megastore.utils;

import com._up.megastore.data.model.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.UUID;

public class OrderSpecifications {
    public static Specification<Order> withStartPeriodDate(Date startPeriodDate) {
        return (root, query, cb) -> startPeriodDate == null ? null :
                cb.greaterThanOrEqualTo(root.get("date"), startPeriodDate);
    }
    public static Specification<Order> withEndPeriodDate(Date endPeriodDate) {
        return (root, query, cb) -> endPeriodDate == null ? null :
                cb.lessThanOrEqualTo(root.get("date"), endPeriodDate);
    }
    public static Specification<Order> withUserId(UUID userId) {
        return (root, query, cb) -> userId == null ? null :
                cb.equal(root.get("user").get("id"), userId);
    }
    public static Specification<Order> withState(String state) {
        return (root, query, cb) -> state == null ? null :
                cb.equal(root.get("state"), state);
    }
}
