package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;

import java.util.UUID;

public interface ITokenService {
    public Token saveToken(User user);
    public User findUserByActivationToken(UUID activationToken);
    public Token findTokenByIdOrThrowException(UUID token);

    Token findActiveTokenByUser(User user);
}
