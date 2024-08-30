package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;

public interface ISizeService {

    SizeResponse saveSize(CreateSizeRequest createSizeRequest);

}