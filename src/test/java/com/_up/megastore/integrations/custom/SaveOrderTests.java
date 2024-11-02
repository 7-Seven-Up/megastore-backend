package com._up.megastore.integrations.custom;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/orders/save_order.sql")
public class SaveOrderTests extends BaseIntegrationTest {

    @MockBean
    private EmailService emailService;

    @Test
    void saveOrder_existentClient() throws Exception {
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

        mockMvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void saveOrder_nonExistentClient() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836c"), List.of(
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
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "User with id 58fae25b-ea38-4e7b-ab2d-9f555a67836c does not exist.");
    }

    @Test
    void saveOrder_existentProduct() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b"), List.of(
                new OrderDetailRequest(
                        UUID.fromString("183f205a-3430-4e11-8bca-57672a0ce3ff"),
                        5
                )
        )
        );

        mockMvc.perform(
                post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void saveOrder_nonExistentProduct() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b"), List.of(
                new OrderDetailRequest(
                        UUID.fromString("183f205a-3430-4e11-8bca-57672a0ce4ff"),
                        5
                )
        )
        );

        String response = mockMvc.perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product with id 183f205a-3430-4e11-8bca-57672a0ce4ff does not exist.");
    }

}
