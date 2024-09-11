package com._up.megastore.controllers.implementations;

import com._up.megastore.controllers.interfaces.ISizeController;
import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.services.interfaces.ISizeService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

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

    @Override
    public SizeResponse updateSize(UUID sizeId , UpdateSizeRequest updateSizeRequest) {
        return sizeService.updateSize(sizeId, updateSizeRequest);
    }

    @Override
    public SizeResponse readSize(UUID sizeId){
        return  sizeService.readSize(sizeId);
    }

    @Override
    public SizeResponse restoreSize(UUID sizeId){
        return sizeService.restoreSize(sizeId);
    }
  
    @Override
    public void deleteSize(UUID sizeId){
        sizeService.deleteSize(sizeId);
    }

    @Override
    public Page<SizeResponse> readAllSizes(int page, int pageSize, String name){ return sizeService.readAllSizes(page, pageSize, name); }
}