package com._up.megastore.controllers.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/api/v1/sizes")
public interface ISizeController {

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    SizeResponse saveSize(@RequestBody CreateSizeRequest createSizeRequest);

}