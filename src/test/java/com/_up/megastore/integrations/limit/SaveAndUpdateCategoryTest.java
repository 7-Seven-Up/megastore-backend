package com._up.megastore.integrations.limit;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.requests.UpdateCategoryRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/categories/save_and_update_categories.sql")
public class SaveAndUpdateCategoryTest extends BaseIntegrationTest {

    @Test
    void saveCategory_blankName() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "", "Test description", null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category name must be between 3 and 20 characters");
    }

    @Test
    void saveCategory_nullName() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                null, "Test description", null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category name must not be null");
    }

    @Test
    void saveCategory_correctName() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Ropa de Moda", "Test description", null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void saveCategory_nullDescription() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Test name", null, null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
    }

    @Test
    void saveCategory_correctDescription() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Test name", "Prendas de vestir ligeras", null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void saveCategory_exceedingDescription() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Test name", "Prendas de vestir buenas, lindas, ligeras, baratas, majestuosas, excelentes, adelgazadoras y sobre todo, humildes", null
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category description must be less than 50 characters");
    }

    @Test
    void updateCategory_blankName() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                "", "Test description", null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category name must be between 3 and 20 characters");
    }

    @Test
    void updateCategory_nullName() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                null, "Test description", null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category name must not be null");
    }

    @Test
    void updateCategory_correctName() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                "Ropa de moda", "Test description", null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void updateCategory_nullDescription() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                "Ropa de moda", null, null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
    }

    @Test
    void updateCategory_correctDescription() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                "Ropa de moda", "Ropa usada recientemente", null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void updateCategory_exceedingDescription() throws Exception {
        UpdateCategoryRequest request = new UpdateCategoryRequest(
                "Ropa de moda", "Ropa usada recientemente por figuras mundiales y que tienen un impacto en el cliente promedio", null
        );

        String response = mockMvc.perform(
                        put("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category description must be less than 50 characters");
    }
}
