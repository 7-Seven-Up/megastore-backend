package com._up.megastore.services.implementations;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import com._up.megastore.data.repositories.ITokenRepository;
import com._up.megastore.services.interfaces.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private final ITokenRepository iTokenRepository;

    public TokenService(ITokenRepository iTokenRepository){
        this.iTokenRepository = iTokenRepository;
    }

    @Override
    public Token saveToken(User user){
        Token token = Token.builder()
                .user(user)
                .build();
        return iTokenRepository.save(token);
    }

    @Override
    public User findUserByActivationToken(UUID activationToken){
        Token token = findTokenByIdOrThrowException(activationToken);
        return token.getUser();
    }

    @Override
    public Token findTokenByIdOrThrowException(UUID token){
        return iTokenRepository.findById(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token with UUID " + token + " does not exist."));
    }
}
