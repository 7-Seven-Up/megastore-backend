package com._up.megastore.integrations.implementations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;


public class SizeControllerTest extends BaseIntegrationTest {

    @Test
    void saveSize() throws Exception {
        CreateSizeRequest request = new CreateSizeRequest("mockedName", "mockedDescription");

        String response = mockMvc.perform(
                post("/api/v1/sizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", "mockedName");
        assertContains(response, "description", "mockedDescription");
    }

    @Test
    @Sql("/scripts/sizes/create_sizes.sql")
    void getDeletedSizes() throws Exception {
        String response = mockMvc.perform(
                get("/api/v1/sizes/deleted")
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertContains(response, "sizeId", "59985182-9b95-40e2-8f8b-f66769fc6847");
        assertContains(response, "sizeId", "e206a421-8e9c-45bd-ad53-4da324a9b34e");
    }
}
