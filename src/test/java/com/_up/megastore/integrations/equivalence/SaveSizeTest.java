package com._up.megastore.integrations.equivalence;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SaveSizeTest extends BaseIntegrationTest {

    private final String BAD_NAME_MESSAGE = "Size name must be between 1 and 20 characters";
    private final String BAD_DESCRIPTION_MESSAGE = "Size description must be less than 50 characters";

    @Test
    void saveSize_emptyName() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("", "Test description");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_NAME_MESSAGE));
    }

    @Test
    void saveSize_nameIsMoreThan1Character() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("XL", "Test description");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void saveSize_nameIsLessThan20Chracters() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("Tamano gigante", "Test description");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void saveSize_nameIsMoreThan20Characters() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("Tamano gigante extra grande", "Test description");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_NAME_MESSAGE));
    }

    @Test
    void saveSize_emptyDescription() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("Test name", "");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void saveSize_descriptionIsLessThan50Characters() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("Test name", "100x50x40cm");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void saveSize_descriptionIsMoreThan50Characters() throws Exception {
        CreateSizeRequest request = buildCreateSizeRequest("Test name", "100 centimetros de largo por 50 centimetros de ancho por 40 centimetros de alto");

        mockMvc.perform(
                        post("/api/v1/sizes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_DESCRIPTION_MESSAGE));
    }

    private CreateSizeRequest buildCreateSizeRequest(String name, String description) {
        return new CreateSizeRequest(name, description);
    }
}
