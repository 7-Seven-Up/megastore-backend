package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.ISizeRepository;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.SizeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public SizeResponse updateSize(UUID sizeId,UpdateSizeRequest updateSizeRequest){
        Size size = findSizeByIdOrThrowException(sizeId);
        size.setName(updateSizeRequest.name());
        size.setDescription(updateSizeRequest.description());

        return SizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public SizeResponse restoreSize(UUID sizeId){
        Size size = findSizeByIdOrThrowException(sizeId);
        ifSizeIsNotDeletedThrowException(size);
        size.setDeleted(false);
        return SizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    public void ifSizeIsNotDeletedThrowException(Size size) {
        if(!size.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size with id " + size.getSizeId() + " is not deleted.");
        }
    } 
    
    @Override
    public void deleteSize(UUID sizeId){
        Size size = findSizeByIdOrThrowException(sizeId);
        ifSizeIsDeletedThrowException(size);
        size.setDeleted(true);

        sizeRepository.save(size);
    }

    public void ifSizeIsDeletedThrowException(Size size){
        if(size.isDeleted()){
            throw new IllegalStateException("Size with id " + size.getSizeId() + " is already deleted.");

        }
    }
}