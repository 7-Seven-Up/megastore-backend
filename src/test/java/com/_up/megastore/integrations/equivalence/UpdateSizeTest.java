package com._up.megastore.integrations.equivalence;

import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/sizes/update_and_delete_sizes.sql")
public class UpdateSizeTest extends BaseIntegrationTest {

    private final String BAD_NAME_MESSAGE = "Size name must be between 1 and 20 characters";
    private final String BAD_DESCRIPTION_MESSAGE = "Size description must be less than 50 characters";

    @Test
    void updateSize_emptyName() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("", "Test description");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_NAME_MESSAGE));
    }

    @Test
    void updateSize_nameIsMoreThan1Characters() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("XL", "Test description");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void updateSize_nameIsLessThan20Characters() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("Tamano gigante", "Test description");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void updateSize_nameIsMoreThan20Characters() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("Tamano gigante extra grande", "Test description");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_NAME_MESSAGE));
    }

    @Test
    void updateSize_emptyDescription() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("Test name", "");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void updateSize_descriptionIsLessThan50Characters() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("Test name", "100x50x40cm");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.description").value(request.description()));
    }

    @Test
    void updateSize_descriptionIsMoreThan50Characters() throws Exception {
        UpdateSizeRequest request = buildUpdateSizeRequest("Test name", "100 centimetros de largo por 50 centimetros de ancho por 40 centimetros de alto");

        mockMvc.perform(
                        put("/api/v1/sizes/cc7f119e-93e0-43f0-8fa4-fcbfd7616265")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_DESCRIPTION_MESSAGE));
    }

    private UpdateSizeRequest buildUpdateSizeRequest(String name, String description) {
        return new UpdateSizeRequest(name, description);
    }

}
