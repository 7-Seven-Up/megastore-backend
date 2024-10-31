package com._up.megastore.integrations.custom;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/categories/save_and_update_categories.sql")
public class SaveCategoryTest extends BaseIntegrationTest {

    @Test
    void saveCategory_existentSuperCategory() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Test name", "Test description", UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b")
        );

        mockMvc.perform(
                post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void saveCategory_nonExistentSubCategory() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(
                "Test name", "Test description", UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836a")
        );

        String response = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category with id 58fae25b-ea38-4e7b-ab2d-9f555a67836a does not exist.");
    }

}
