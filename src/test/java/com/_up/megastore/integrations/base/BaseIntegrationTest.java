package com._up.megastore.integrations.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected String toJson(Object request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    protected void assertContains(String response, String propertyName, String value) {
        String valueToCheck = buildValueToCheck(propertyName, value);
        Assertions.assertTrue(response.contains(valueToCheck));
    }

    protected void assertNotContains(String response, String propertyName, String value) {
        String valueToCheck = buildValueToCheck(propertyName, value);
        Assertions.assertFalse(response.contains(valueToCheck));
    }

    private String buildValueToCheck(String propertyName, String value) {
        return "\"" + propertyName + "\"" + ":" + "\"" + value + "\"";
    }
}
