package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;

import java.util.UUID;

public interface ISizeService {

    SizeResponse saveSize(CreateSizeRequest createSizeRequest);

    Size findSizeByIdOrThrowException(UUID sizeId);

    SizeResponse updateSize(UUID sizeId, UpdateSizeRequest updateSizeRequest);

    void deleteSize(UUID sizeId);
}