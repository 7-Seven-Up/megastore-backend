package com._up.megastore.services.interfaces;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;

import java.util.UUID;

public interface ITokenService {
    public Token saveToken(User user);
    public UUID findUser(UUID activationToken);
}
