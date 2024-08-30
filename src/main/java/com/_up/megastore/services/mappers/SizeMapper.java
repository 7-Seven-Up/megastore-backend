package com._up.megastore.services.mappers;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;

public class SizeMapper {

    public static SizeResponse toSizeResponse(Size size) {
        return new SizeResponse(size.getSizeId(), size.getName(), size.getDescription());
    }

    public static Size toSize(CreateSizeRequest createSizeRequest) {
        return Size.builder()
                .name(createSizeRequest.name())
                .description(createSizeRequest.description())
                .build();
    }

}