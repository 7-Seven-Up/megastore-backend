package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.ISizeRepository;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.SizeMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SizeService implements ISizeService {

    private final ISizeRepository sizeRepository;

    public SizeService(ISizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public SizeResponse saveSize(CreateSizeRequest createSizeRequest) {
        Size size = SizeMapper.toSize(createSizeRequest);
        return SizeMapper.toSizeResponse( sizeRepository.save(size) );
    }

    @Override
    public Size findSizeByIdOrThrowException(UUID sizeId) {
        return sizeRepository.findById(sizeId)
                .orElseThrow( () -> new NoSuchElementException("Size with id " + sizeId + " does not exist.") );
    }

}