package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.ISizeController;
import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.services.interfaces.ISizeService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SizeController implements ISizeController {

    private final ISizeService sizeService;

    public SizeController(ISizeService sizeService) {
        this.sizeService = sizeService;
    }

    @Override
    public SizeResponse saveSize(CreateSizeRequest createSizeRequest) {
        return sizeService.saveSize(createSizeRequest);
    }

}