package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {
    @Query("SELECT t FROM tokens t " +
           "WHERE t.user = ?1 " +
           "AND t.tokenExpirationDate > CURRENT_TIMESTAMP ")
    Optional<Token> findActiveTokenByUser(User user);
}
