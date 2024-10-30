package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;

import java.util.UUID;

public interface ITokenService {

    Token saveToken(User user);

    User findUserByToken(UUID token);

    Token findTokenByIdOrThrowException(UUID token);

    Token findActiveTokenByUser(User user);

}