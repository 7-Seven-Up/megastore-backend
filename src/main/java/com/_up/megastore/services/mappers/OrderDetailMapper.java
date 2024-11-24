package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.controllers.responses.OrderDetailResponse;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderDetail;
import com._up.megastore.data.model.Product;

public class OrderDetailMapper {

    private OrderDetailMapper() {}

    public static OrderDetail toOrderDetail(
            Order order,
            Product product,
            Double subtotal,
            OrderDetailRequest orderDetailRequest
    ) {
        return OrderDetail.builder()
                .product(product)
                .quantity(orderDetailRequest.quantity())
                .order(order)
                .subtotal(subtotal)
                .build();
    }

    public static OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getOrderDetailId(),
                orderDetail.getQuantity(),
                ProductMapper.toProductResponse(orderDetail.getProduct())
        );
    }
}
