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
    public User findUserByToken(UUID tokenId) {
        return findTokenByIdOrThrowException(tokenId).getUser();
    }

    @Override
    public Token findTokenByIdOrThrowException(UUID token) {
        return iTokenRepository.findById(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token with UUID " + token + " does not exist."));
    }

    @Override
    public Token findActiveTokenByUser(User user) {
        return iTokenRepository.findActiveTokenByUser(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User activation token has expired"));
    }
}
