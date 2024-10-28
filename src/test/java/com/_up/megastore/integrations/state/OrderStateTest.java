package com._up.megastore.integrations.state;

import com._up.megastore.controllers.requests.CancelOrderRequest;
import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.data.enums.State;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import com._up.megastore.services.implementations.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderStateTest extends BaseIntegrationTest {

    @MockBean
    private EmailService emailService;

    @Test
    @Sql("/scripts/orders/save_order.sql")
    void saveOrderWithExceedingQuantity() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b"), List.of(
                new OrderDetailRequest(
                        UUID.fromString("183f205a-3430-4e11-8bca-57672a0ce3ff"),
                        15
                )
        )
        );

        mockMvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/scripts/orders/finish_order.sql")
    void finishOrder() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/95803676-823b-4454-9844-904d617f42e7/finish")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", State.FINISHED.name());

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @Sql("/scripts/orders/finish_order.sql")
    void finishOrderWithIncompatibleState() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/e4990fed-48f6-40ab-b7a8-de242a57ab41/finish")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", State.FINISHED.exceptionMessage);
    }

    @Test
    @Sql("/scripts/orders/mark_order_in_delivery.sql")
    void markOrderInDelivery() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/95803676-823b-4454-9844-904d617f42e2/mark-in-delivery")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", State.IN_DELIVERY.name());

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @Sql("/scripts/orders/mark_order_in_delivery.sql")
    void markOrderInDeliveryWithIncompatibleState() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/e4990fed-48f6-40ab-b7a8-de242a57ab40/mark-in-delivery")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", State.IN_DELIVERY.exceptionMessage);
    }

    @Test
    @Sql("/scripts/orders/deliver_order.sql")
    void deliverOrder() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/95803676-823b-4454-9844-904d617f42e2/deliver")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", State.DELIVERED.name());

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @Sql("/scripts/orders/deliver_order.sql")
    void deliverOrderWithIncompatibleState() throws Exception {
        String response = mockMvc.perform(
                        post("/api/v1/orders/e4990fed-48f6-40ab-b7a8-de242a57ab40/deliver")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", State.DELIVERED.exceptionMessage);
    }

    @Test
    @Sql("/scripts/orders/cancel_order.sql")
    void cancelOrderWithOrderInProgress() throws Exception {
        final var cancelOrderRequest = new CancelOrderRequest("Bad products");

        String response = mockMvc.perform(
                        post("/api/v1/orders/95803676-823b-4454-9844-904d617f42e2/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(cancelOrderRequest))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", State.CANCELLED.name());
    }

    @Test
    @Sql("/scripts/orders/cancel_order.sql")
    void cancelOrderWithOrderFinished() throws Exception {
        final var cancelOrderRequest = new CancelOrderRequest("Bad products");

        String response = mockMvc.perform(
                        post("/api/v1/orders/e4990fed-48f6-40ab-b7a8-de242a57ab40/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(cancelOrderRequest))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", State.CANCELLED.name());
    }

    @Test
    @Sql("/scripts/orders/cancel_order.sql")
    void cancelOrdeWithIncompatibleState() throws Exception {
        final var cancelOrderRequest = new CancelOrderRequest("Bad products");

        String response = mockMvc.perform(
                        post("/api/v1/orders/1b8f7aef-7154-4f82-b48f-988556d74cad/cancel")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(cancelOrderRequest))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", State.CANCELLED.exceptionMessage);
    }
}
