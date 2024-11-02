package com._up.megastore.integrations.custom;

import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/categories/delete_category.sql")
public class DeleteCategoryTest extends BaseIntegrationTest {

    @Test
    void deleteCategory_categoryDoesNotHaveSubcategories() throws Exception {
        mockMvc.perform(
                delete("/api/v1/categories/58fae25b-ea38-4e7b-ab2d-9f555a67836b")
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteCategory_categoryHasSubcategories() throws Exception {
        String response = mockMvc.perform(
                        delete("/api/v1/categories/7927fb16-d23b-43f5-95c8-6c82bb333331")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Category has subcategories and cannot be deleted.");
    }
}
