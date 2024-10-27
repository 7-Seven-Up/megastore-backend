package com._up.megastore.services.implementations;

import com._up.megastore.data.enums.State;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IOrderRepository;
import com._up.megastore.utils.EmailBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private EmailBuilder emailBuilder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderService orderService;

    private final UUID orderId = UUID.randomUUID();
    private final Order order = spy(Order.class);
    private final User user = mock(User.class);

    @BeforeEach
    void setUp() {
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));
    }

    @Nested
    class WhenStatesAreCompatible {

        @BeforeEach
        void setUp() {
            when(orderRepository.save(any(Order.class))).thenReturn(order);
            when(orderRepository.getOrderTotal(any(Order.class))).thenReturn(1000.0);
            when(order.getUser()).thenReturn(user);
            when(user.getEmail()).thenReturn("User email");
            when(emailBuilder.buildOrderEmail(any(Order.class), anyString())).thenReturn("Email content");

            doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        }

        @Test
        void finishOrder_orderIsInProgress() {
            order.setState(State.IN_PROGRESS);

            assertDoesNotThrow(() -> {
                final var orderResponse = orderService.finishOrder(orderId);
                assertEquals(orderResponse.state(), State.FINISHED.name());
            });
        }

        @Test
        void markOrderInDelivery_orderIsFinished() {
            order.setState(State.FINISHED);

            assertDoesNotThrow(() -> {
                final var orderResponse = orderService.markOrderInDelivery(orderId);
                assertEquals(orderResponse.state(), State.IN_DELIVERY.name());
            });
        }

        @Test
        void deliverOrder_orderIsInDelivery() {
            order.setState(State.IN_DELIVERY);

            assertDoesNotThrow(() -> {
                final var orderResponse = orderService.deliverOrder(orderId);
                assertEquals(orderResponse.state(), State.DELIVERED.name());
            });
        }

        @Test
        void cancelOrder_orderIsInProgress() {
            order.setState(State.IN_PROGRESS);

            assertDoesNotThrow(() -> {
                final var orderResponse = orderService.cancelOrder(orderId);
                assertEquals(orderResponse.state(), State.CANCELLED.name());
            });
        }

        @Test
        void cancelOrder_orderIsFinished() {
            order.setState(State.FINISHED);

            assertDoesNotThrow(() -> {
                final var orderResponse = orderService.cancelOrder(orderId);
                assertEquals(orderResponse.state(), State.CANCELLED.name());
            });
        }
    }

    @Nested
    class WhenStatesAreIncompatible {

        @Test
        void finishOrder_orderIsFinished() {
            order.setState(State.FINISHED);
            assertThrows(ResponseStatusException.class, () -> orderService.finishOrder(orderId));
        }

        @Test
        void finishOrder_orderIsInDelivery() {
            order.setState(State.IN_DELIVERY);
            assertThrows(ResponseStatusException.class, () -> orderService.finishOrder(orderId));
        }

        @Test
        void finishOrder_orderIsDelivered() {
            order.setState(State.DELIVERED);
            assertThrows(ResponseStatusException.class, () -> orderService.finishOrder(orderId));
        }

        @Test
        void finishOrder_orderIsCancelled() {
            order.setState(State.CANCELLED);
            assertThrows(ResponseStatusException.class, () -> orderService.finishOrder(orderId));
        }

        @Test
        void markOrderInDelivery_orderIsInProgress() {
            order.setState(State.IN_PROGRESS);
            assertThrows(ResponseStatusException.class, () -> orderService.markOrderInDelivery(orderId));
        }

        @Test
        void markOrderInDelivery_orderIsInDelivery() {
            order.setState(State.IN_DELIVERY);
            assertThrows(ResponseStatusException.class, () -> orderService.markOrderInDelivery(orderId));
        }

        @Test
        void markOrderInDelivery_orderIsDelivered() {
            order.setState(State.DELIVERED);
            assertThrows(ResponseStatusException.class, () -> orderService.markOrderInDelivery(orderId));
        }

        @Test
        void markOrderInDelivery_orderIsCancelled() {
            order.setState(State.CANCELLED);
            assertThrows(ResponseStatusException.class, () -> orderService.markOrderInDelivery(orderId));
        }

        @Test
        void deliverOrder_orderIsInProgress() {
            order.setState(State.IN_PROGRESS);
            assertThrows(ResponseStatusException.class, () -> orderService.deliverOrder(orderId));
        }

        @Test
        void deliverOrder_orderIsFinished() {
            order.setState(State.FINISHED);
            assertThrows(ResponseStatusException.class, () -> orderService.deliverOrder(orderId));
        }

        @Test
        void deliverOrder_orderIsDelivered() {
            order.setState(State.DELIVERED);
            assertThrows(ResponseStatusException.class, () -> orderService.deliverOrder(orderId));
        }

        @Test
        void deliverOrder_orderIsCancelled() {
            order.setState(State.CANCELLED);
            assertThrows(ResponseStatusException.class, () -> orderService.deliverOrder(orderId));
        }

        @Test
        void cancelOrder_orderIsInDelivery() {
            order.setState(State.IN_DELIVERY);
            assertThrows(ResponseStatusException.class, () -> orderService.cancelOrder(orderId));
        }

        @Test
        void cancelOrder_orderIsDelivered() {
            order.setState(State.DELIVERED);
            assertThrows(ResponseStatusException.class, () -> orderService.cancelOrder(orderId));
        }

        @Test
        void cancelOrder_orderIsCancelled() {
            order.setState(State.CANCELLED);
            assertThrows(ResponseStatusException.class, () -> orderService.cancelOrder(orderId));
        }
    }
}