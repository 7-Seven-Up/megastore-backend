package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Token;
import com._up.megastore.data.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {

    @Transactional
    @Modifying
    @Query("UPDATE tokens SET tokenExpirationDate = now() WHERE user = ?1")
    void expireUserTokens(User user);
}
