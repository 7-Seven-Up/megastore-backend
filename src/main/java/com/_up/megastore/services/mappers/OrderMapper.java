package com._up.megastore.services.mappers;

import com._up.megastore.controllers.responses.OrderDetailResponse;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderDetail;
import com._up.megastore.data.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toOrder(User user, Integer number) {
        return Order.builder()
                .user(user)
                .number(number)
                .build();
    }

    public static OrderResponse toOrderResponse(
            Order order,
            Double total) {
        return new OrderResponse(
                order.getOrderId(),
                order.getNumber(),
                order.getDate(),
                order.getUser().getFullName(),
                order.getState().name(),
                total,
                buildOrderDetailsResponse(order.getOrderDetails())
        );
    }

    private static List<OrderDetailResponse> buildOrderDetailsResponse(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailMapper::toOrderDetailResponse)
                .collect(Collectors.toList());
    }
}
