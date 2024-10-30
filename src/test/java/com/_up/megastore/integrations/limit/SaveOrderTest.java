package com._up.megastore.integrations.limit;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/orders/save_order.sql")
public class SaveOrderTest extends BaseIntegrationTest {

    @MockBean
    private EmailService emailService;

    @Test
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
                .andExpect(jsonPath("$.total").value(80000.0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "clientName", "Client Name");
        assertContains(response, "state", "IN_PROGRESS");

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
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
}
