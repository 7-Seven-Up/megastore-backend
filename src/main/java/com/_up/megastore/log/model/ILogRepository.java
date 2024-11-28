package com._up.megastore.log.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ILogRepository extends MongoRepository<Log, UUID> {
}