package com._up.megastore.data.repositories;

import com._up.megastore.data.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IStateRepository extends JpaRepository<State, UUID> {}