package com._up.megastore.integrations.state;

import com._up.megastore.controllers.requests.ActivateUserRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import com._up.megastore.services.implementations.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/users/activate_user.sql")
public class UserStateTest extends BaseIntegrationTest {

    @MockBean
    private EmailService emailService;

    @Test
    void activateUser_userIsNotActivated() throws Exception {
        doNothing().when(emailService).sendEmail(any(), any(), any());

        ActivateUserRequest request = new ActivateUserRequest(
                UUID.fromString("834fe4c1-d77d-4bf0-8e29-3cddbc245416")
        );

        mockMvc.perform(
                post("/api/v1/users/1f8b460d-3575-4c60-961b-fb15116454d1/activate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isOk());
    }

    @Test
    void activateUser_userIsActivated() throws Exception {
        ActivateUserRequest request = new ActivateUserRequest(
                UUID.fromString("7927fb16-d23b-43f5-95c8-6c82bb333331")
        );

        String response = mockMvc.perform(
                        post("/api/v1/users/06ca2421-8be3-4cb8-92b6-32a2fe6266e9/activate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(request))
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "User is already activated.");
    }

}
