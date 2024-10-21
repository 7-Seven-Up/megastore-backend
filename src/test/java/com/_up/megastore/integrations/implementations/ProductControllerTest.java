package com._up.megastore.integrations.implementations;

import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/products/get_variants.sql")
class ProductControllerTest extends BaseIntegrationTest {

    @Test
    void getProductVariants() throws Exception {
        String response = mockMvc.perform(
                        get("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff/variants")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", "Variant name");
        assertContains(response, "description", "Variant description");
    }
}