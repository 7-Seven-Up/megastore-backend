package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderDetail;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IOrderRepository;
import com._up.megastore.services.interfaces.IOrderDetailService;
import com._up.megastore.services.interfaces.IOrderService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final IUserService userService;
    private final IOrderDetailService orderDetailService;
    private final IOrderRepository orderRepository;

    public OrderService(
            IUserService userService,
            IOrderDetailService orderDetailService,
            IOrderRepository orderRepository
    ) {
        this.userService = userService;
        this.orderDetailService = orderDetailService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        User user = userService.findUserByIdOrThrowException(createOrderRequest.userId());
        Integer number = getNextOrderNumber();
        Order order = orderRepository.save( OrderMapper.toOrder(user, number) );

        List<OrderDetail> orderDetails = orderDetailService.saveOrderDetails(createOrderRequest.orderDetailRequestList(), order);
        Double total = getOrderTotal(orderDetails);

        return OrderMapper.toOrderResponse(order, total, orderDetails);
    }

    private synchronized Integer getNextOrderNumber() {
        return orderRepository.getLastOrderNumber() + 1;
    }

    private Double getOrderTotal(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .mapToDouble(orderDetail -> orderDetail.getPriceToDate() * orderDetail.getQuantity())
                .sum();
    }
}
