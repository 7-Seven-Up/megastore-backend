package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.requests.UpdateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.ISizeRepository;
import com._up.megastore.services.interfaces.ISizeService;
import com._up.megastore.services.mappers.SizeMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Transactional
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
    @Transactional
    public SizeResponse updateSize(UUID sizeId,UpdateSizeRequest updateSizeRequest){
        Size size = findSizeByIdOrThrowException(sizeId);
        size.setName(updateSizeRequest.name());
        size.setDescription(updateSizeRequest.description());

        return SizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public SizeResponse readSize(UUID sizeId){
        Size size = findSizeByIdOrThrowException(sizeId);
        return SizeMapper.toSizeResponse(size);
    }

    @Override
    public Page<SizeResponse> readAllSizes(int page, int pageSize, String name){
        Pageable pageable = PageRequest.of(page, pageSize);
        return sizeRepository.findSizeByDeletedIsFalseAndNameContainingIgnoreCase(name,pageable)
                .map(SizeMapper::toSizeResponse);
    }

    @Override
    public SizeResponse restoreSize(UUID sizeId){
        Size size = findSizeByIdOrThrowException(sizeId);
        validateSizeForRestoration(size);
        size.setDeleted(false);
        return SizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public void deleteSize(UUID sizeId){
        Size size = findSizeByIdOrThrowException(sizeId);
        throwExceptionIfSizeIsDeleted(size);
        size.setDeleted(true);

        sizeRepository.save(size);
    }

    private void validateSizeForRestoration(Size size){
        throwExceptionIfSizeIsNotDeleted(size);
        throwExceptionIfSizeNameAlreadyExists(size);
    }

    public void throwExceptionIfSizeIsNotDeleted(Size size) {
        if(!size.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size with id " + size.getSizeId() + " is not deleted.");
        }
    }

    public void throwExceptionIfSizeIsDeleted(Size size){
        if(size.isDeleted()){
            throw new IllegalStateException("Size with id " + size.getSizeId() + " is already deleted.");

        }
    }

    private void throwExceptionIfSizeNameAlreadyExists(Size size){
        if (sizeRepository.existsByNameAndDeletedIsFalse(size.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size with name "+ size.getName() +" already exists and is not deleted.");
        }
    }
}
