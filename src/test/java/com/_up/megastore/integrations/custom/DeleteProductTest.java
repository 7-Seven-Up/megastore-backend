package com._up.megastore.integrations.custom;

import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/products/get_and_delete_variants.sql")
public class DeleteProductTest extends BaseIntegrationTest {

    @Test
    void deleteProduct_productDoesNotHaveVariants() throws Exception {
        mockMvc.perform(
                delete("/api/v1/products/22ede130-726f-49ac-9564-d783fc22a6fa")
        ).andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_productHasVariants() throws Exception {
        String response = mockMvc.perform(
                        delete("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product has variants and it can't be deleted.");
    }

}
