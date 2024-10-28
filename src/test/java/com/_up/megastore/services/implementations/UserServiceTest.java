package com._up.megastore.services.implementations;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.IUserRepository;
import com._up.megastore.utils.EmailBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailBuilder emailBuilder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private final Token token = mock(Token.class);
    private final User user = mock(User.class);

    @Nested
    class ActivateUserStateChanges {

        @BeforeEach
        void setUp() {
            when(tokenService.findUserByActivationToken(any(UUID.class))).thenReturn(user);
        }

        @Test
        void activateUser_userIsNotActivated() {
            when(tokenService.findTokenByIdOrThrowException(any(UUID.class))).thenReturn(token);
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(emailBuilder.buildWelcomeEmail(any(User.class))).thenReturn("Email content");
            doNothing().when(emailService).sendEmail(any(), any(), any());

            when(user.isActivated()).thenReturn(false);
            when(token.getTokenExpirationDate()).thenReturn(LocalDateTime.now().plusMinutes(10));

            assertDoesNotThrow(() -> userService.activateUser(UUID.randomUUID(), UUID.randomUUID()));
        }

        @Test
        void activateUser_userIsActivated() {
            when(user.isActivated()).thenReturn(true);
            assertThrows(IllegalArgumentException.class, () -> userService.activateUser(UUID.randomUUID(), UUID.randomUUID()));
        }
    }

    @Nested
    class ActivateUserLimitTesting {

        MockedStatic<LocalDateTime> mockedDateTime;

        LocalDateTime expectedDate = LocalDateTime.of(2024, 10, 25, 14, 45);
        LocalDateTime expiredDate = expectedDate.minusMinutes(1);
        LocalDateTime notExpiredDate = expectedDate.plusMinutes(1);

        @BeforeEach
        void setUp() {
            mockedDateTime = mockStatic(LocalDateTime.class);
            mockedDateTime.when(LocalDateTime::now).thenReturn(expectedDate);

            when(tokenService.findUserByActivationToken(any(UUID.class))).thenReturn(user);
            when(tokenService.findTokenByIdOrThrowException(any(UUID.class))).thenReturn(token);

            when(user.isActivated()).thenReturn(false);
        }

        @AfterEach
        void tearDown() {
            mockedDateTime.close();
        }

        @Test
        void activateUser_tokenIsNotExpired() {
            when(userRepository.save(any(User.class))).thenReturn(user);
            when(emailBuilder.buildWelcomeEmail(any(User.class))).thenReturn("Email content");
            doNothing().when(emailService).sendEmail(any(), any(), any());

            when(token.getTokenExpirationDate()).thenReturn(notExpiredDate);
            assertDoesNotThrow(() -> userService.activateUser(UUID.randomUUID(), UUID.randomUUID()));
        }

        @Test
        void activateUser_tokenIsExpired() {
            when(token.getTokenExpirationDate()).thenReturn(expiredDate);
            assertThrows(ResponseStatusException.class, () -> userService.activateUser(UUID.randomUUID(), UUID.randomUUID()));
        }
    }

}