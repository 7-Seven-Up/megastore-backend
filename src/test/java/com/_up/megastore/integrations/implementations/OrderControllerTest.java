package com._up.megastore.integrations.implementations;

import com._up.megastore.controllers.requests.CreateOrderRequest;
import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/orders/save_order.sql")
class OrderControllerTest extends BaseIntegrationTest {

    @Test
    void saveProduct() throws Exception {
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
    void saveProductWithExceedingQuantity() throws Exception {
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