package com._up.megastore.controllers.interfaces;

import java.util.UUID;

import com._up.megastore.controllers.responses.ProductResponse;

public interface IGetProductController {
    ProductResponse getById(UUID id);
}
