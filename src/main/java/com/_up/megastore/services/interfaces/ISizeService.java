package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ISizeService {

    SizeResponse saveSize(CreateSizeRequest createSizeRequest);

    Size findSizeByIdOrThrowException(UUID sizeId);

    SizeResponse updateSize(UUID sizeId, UpdateSizeRequest updateSizeRequest);

    Page<SizeResponse> readAllSizes(int page, int pageSize, String name);

    SizeResponse readSize(UUID sizeId);

    SizeResponse restoreSize (UUID sizeId);

    void deleteSize(UUID sizeId);
}