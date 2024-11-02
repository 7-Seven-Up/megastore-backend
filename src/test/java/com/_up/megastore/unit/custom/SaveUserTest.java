package com._up.megastore.unit.custom;

import com._up.megastore.controllers.requests.SignUpRequest;
import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.services.implementations.EmailService;
import com._up.megastore.services.implementations.TokenService;
import com._up.megastore.services.implementations.UserService;
import com._up.megastore.services.mappers.UserMapper;
import com._up.megastore.utils.EmailBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveUserTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private EmailBuilder emailBuilder;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private User user;

    @Mock
    private Token token;

    @InjectMocks
    private UserService userService;

    MockedStatic<UserMapper> mockedUserMapper;

    @BeforeEach
    void setUp() {
        mockedUserMapper = mockStatic(UserMapper.class);
        when(userRepository.existsByEmail(anyString())).thenAnswer(invocationOnMock ->
                "test@gmail.com".equals(invocationOnMock.getArgument(0)));
    }

    @AfterEach
    void tearDown() {
        mockedUserMapper.close();
    }

    @Test
    void saveUser_existentEmail() {
        SignUpRequest signUpRequest = createSignUpRequest("test@gmail.com", "test2");
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(signUpRequest));
    }

    @Test
    void saveUser_existentUsername() {
        when(userRepository.existsByUsername(any())).thenAnswer(invocationOnMock ->
                "test".equals(invocationOnMock.getArgument(0)));

        SignUpRequest signUpRequest = createSignUpRequest("test2@gmail.com", "test");
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(signUpRequest));
    }

    @Test
    void saveUser_nonExistentEmailAndUsername() {
        mockSuccessfulUserSave();

        SignUpRequest signUpRequest = createSignUpRequest("test2@gmail.com", "test2");
        assertDoesNotThrow(() -> userService.saveUser(signUpRequest));
    }

    private SignUpRequest createSignUpRequest(String email, String username) {
        return new SignUpRequest(email, "Test User", "password", "3533405060", username);
    }

    private void mockSuccessfulUserSave() {
        when(userRepository.existsByUsername(any())).thenAnswer(invocationOnMock ->
                "test".equals(invocationOnMock.getArgument(0)));

        when(passwordEncoder.encode(anyString())).thenReturn("Encoded password");
        when(userRepository.save(user)).thenReturn(user);
        when(tokenService.saveToken(user)).thenReturn(token);
        when(emailBuilder.buildActivationEmailBody(any(), any())).thenReturn("Email content");

        doNothing().when(emailService).sendEmail(any(), any(), any());

        mockedUserMapper.when(() -> UserMapper.toUser(any(SignUpRequest.class))).thenReturn(user);
    }
}
