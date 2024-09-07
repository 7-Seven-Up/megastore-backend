package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sizes")
public interface ISizeController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    SizeResponse saveSize(@RequestBody @Valid CreateSizeRequest createSizeRequest);


    @PutMapping (value = "/{sizeId}") @ResponseStatus(HttpStatus.OK)
    SizeResponse updateSize(@PathVariable UUID sizeId, @RequestBody @Valid UpdateSizeRequest updateSizeRequest);

    @PostMapping (value = "{/sizeId}/restore") @ResponseStatus(HttpStatus.OK)
    SizeResponse restoreSize(@PathVariable UUID sizeId);
}