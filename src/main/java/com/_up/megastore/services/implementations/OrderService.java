package com._up.megastore.services.implementations;

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
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

        Double total = orderRepository.getOrderTotal(order);

        return OrderMapper.toOrderResponse(order, total);
    }

    @Override
    public Order findOrderByIdOrThrowException(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with the id requested does not exist."));
    }

    @Override
    public OrderResponse finishOrder(UUID id) {
        Order order = findOrderByIdOrThrowException(id);
        throwExceptionIfStateIsNotInProgress(order);
        order.setState(State.FINISHED);

        String emailBody = emailBuilder.buildFinishOrderEmail(order);
        emailService.sendEmail(order.getUser().getEmail(), "Order Finished", emailBody);

        return OrderMapper.toOrderResponse(
                orderRepository.save(order),
                orderRepository.getOrderTotal(order)
        );
    }

    @Override
    public OrderResponse markOrderInDelivery(UUID orderId) {
        Order order = findOrderByIdOrThrowException(orderId);
        throwExceptionIfStateIsNotFinished(order);
        order.setState(State.IN_DELIVERY);

        String emailBody = emailBuilder.buildMarkOrderInDeliveryEmail(order);
        emailService.sendEmail(order.getUser().getEmail(), "Order In Delivery", emailBody);

        return OrderMapper.toOrderResponse(
                orderRepository.save(order),
                orderRepository.getOrderTotal(order)
        );
    }

    private synchronized Integer getNextOrderNumber() {
        return orderRepository.getLastOrderNumber() + 1;
    }

    private void throwExceptionIfStateIsNotInProgress(Order order) {
        if (order.getState() != State.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not in progress.");
        }
    }

    private void throwExceptionIfStateIsNotFinished(Order order) {
        if (order.getState() != State.FINISHED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is not finished.");
        }
    }
}
