package com._up.megastore.integrations.implementations;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class SizeControlletTest extends BaseIntegrationTest {

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
}
