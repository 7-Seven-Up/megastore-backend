package com._up.megastore.integrations.implementations;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import com._up.megastore.services.implementations.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends BaseIntegrationTest {

    @MockBean
    private EmailService emailService;

    @Test
    @Sql("/scripts/orders/save_order.sql")
    void saveOrder() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b"), List.of(
                new OrderDetailRequest(
                        UUID.fromString("183f205a-3430-4e11-8bca-57672a0ce3ff"),
                        5
                ),
                new OrderDetailRequest(
                        UUID.fromString("22ede130-726f-49ac-9564-d783fc22a6fa"),
                        3
                )
        )
        );

        String response = mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "clientName", "Client Name");
        assertContains(response, "state", "IN_PROGRESS");
    }

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
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        String response = mockMvc.perform(
                        post("/api/v1/orders/95803676-823b-4454-9844-904d617f42e7/finish")
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "state", "FINISHED");

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @Sql("/scripts/orders/finish_order.sql")
    void finishOrderWithIncompatibleState() throws Exception {
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        String response = mockMvc.perform(
                        post("/api/v1/orders/e4990fed-48f6-40ab-b7a8-de242a57ab41/finish")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Order is not in progress.");
    }

}