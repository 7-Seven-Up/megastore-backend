package com._up.megastore.data.repositories;

import com._up.megastore.data.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAddressRepository extends JpaRepository<Address, UUID> {}