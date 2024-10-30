package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.responses.OrderResponse;
import com._up.megastore.data.enums.State;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IOrderRepository;
import com._up.megastore.services.interfaces.IEmailService;
import com._up.megastore.services.interfaces.IOrderDetailService;
import com._up.megastore.services.interfaces.IOrderService;
import com._up.megastore.services.interfaces.IUserService;
import com._up.megastore.services.mappers.OrderMapper;
import com._up.megastore.utils.EmailBuilder;
import com._up.megastore.utils.OrderSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    private final IUserService userService;
    private final IOrderDetailService orderDetailService;
    private final IEmailService emailService;
    private final EmailBuilder emailBuilder;
    private final IOrderRepository orderRepository;

    public OrderService(
            IUserService userService,
            IOrderDetailService orderDetailService,
            IEmailService emailService,
            EmailBuilder emailBuilder,
            IOrderRepository orderRepository
    ) {
        this.userService = userService;
        this.orderDetailService = orderDetailService;
        this.emailService = emailService;
        this.emailBuilder = emailBuilder;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        User user = userService.findUserByIdOrThrowException(createOrderRequest.userId());
        Integer number = getNextOrderNumber();
        Order order = orderRepository.save(OrderMapper.toOrder(user, number));

        order.setOrderDetails(orderDetailService.saveOrderDetails(createOrderRequest.orderDetailRequestList(), order));
        sendOrderEmail(order, "Order Created");

        return OrderMapper.toOrderResponse(
                order,
                orderRepository.getOrderTotal(order)
        );
    }

    @Override
    public Order findOrderByIdOrThrowException(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with the id requested does not exist."));
    }

    @Override
    public OrderResponse finishOrder(UUID orderId) {
        return changeOrderState(orderId, State.FINISHED);
    }

    @Override
    public OrderResponse markOrderInDelivery(UUID orderId) {
        return changeOrderState(orderId, State.IN_DELIVERY);
    }

    @Override
    public OrderResponse deliverOrder(UUID orderId) {
        return changeOrderState(orderId, State.DELIVERED);
    }

    @Override
    public OrderResponse cancelOrder(UUID orderId, CancelOrderRequest cancelOrderRequest) {
        return changeOrderState(orderId, State.CANCELLED, cancelOrderRequest.reason());
    }

    private OrderResponse changeOrderState(UUID orderId, State newState) {
        return changeOrderState(orderId, newState, null);
    }

    private OrderResponse changeOrderState(UUID orderId, State newState, String reasonToCancel) {
        Order order = findOrderByIdOrThrowException(orderId);
        throwExceptionIfCurrentStateIsIncompatible(order, newState);
        order.setState(newState);

        if (newState == State.CANCELLED)
            order.setReasonToCancel(reasonToCancel);

        sendOrderEmail(order, newState.subject);

        return OrderMapper.toOrderResponse(
                orderRepository.save(order),
                orderRepository.getOrderTotal(order)
        );
    }

    private synchronized Integer getNextOrderNumber() {
        return orderRepository.getLastOrderNumber() + 1;
    }

    private void sendOrderEmail(Order order, String subject) {
        String emailBody = emailBuilder.buildOrderEmail(order, subject);
        emailService.sendEmail(order.getUser().getEmail(), subject, emailBody);
    }

    public void throwExceptionIfCurrentStateIsIncompatible(Order order, State newState) {
        if (!newState.previousStates.contains(order.getState())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, newState.exceptionMessage);
        }
    }

    @Override
    public List<OrderResponse> getOrders(int page, int pageSize,Date startPeriodDate, Date endPeriodDate, UUID userId, String state) {
        Pageable pageable = PageRequest.of(page, pageSize);
         return orderRepository.findAll(
                Specification.where(OrderSpecifications.withStartPeriodDate(startPeriodDate))
                        .and(OrderSpecifications.withEndPeriodDate(endPeriodDate))
                        .and(OrderSpecifications.withUserId(userId))
                        .and(OrderSpecifications.withState(state)),  pageable
        ).map(order -> OrderMapper.toOrderResponse(order, orderRepository.getOrderTotal(order))).toList();
    }
}
