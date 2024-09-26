package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {

}
