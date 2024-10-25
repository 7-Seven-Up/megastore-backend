package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/sizes")
public interface ISizeController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    SizeResponse saveSize(@RequestBody @Valid CreateSizeRequest createSizeRequest);

    @PutMapping (value = "/{sizeId}") @ResponseStatus(HttpStatus.OK)
    SizeResponse updateSize(@PathVariable UUID sizeId, @RequestBody @Valid UpdateSizeRequest updateSizeRequest);

    @GetMapping (value = "/{sizeId}") @ResponseStatus(HttpStatus.OK)
    SizeResponse readSize(@PathVariable UUID sizeId);

    @GetMapping @ResponseStatus(HttpStatus.OK)
    Page<SizeResponse> readAllSizes(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "15") int pageSize,
                                    @RequestParam(defaultValue = "") String name);

    @GetMapping(value = "/deleted")
    @ResponseStatus(HttpStatus.OK)
    Page<SizeResponse> readDeletedSizes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int pageSize,
        @RequestParam(defaultValue = "") String name
    );

    @PostMapping (value = "/{sizeId}/restore") @ResponseStatus(HttpStatus.OK)
    SizeResponse restoreSize(@PathVariable UUID sizeId);

    @DeleteMapping (value = "/{sizeId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSize(@PathVariable UUID sizeId);

}