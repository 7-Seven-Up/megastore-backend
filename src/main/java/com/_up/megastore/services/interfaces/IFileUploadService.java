package com._up.megastore.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    String uploadImage(MultipartFile multipartFile);

}