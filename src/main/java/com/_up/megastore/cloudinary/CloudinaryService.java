package com._up.megastore.cloudinary;

import com._up.megastore.exception.custom_exceptions.UploadFileException;
import com._up.megastore.services.interfaces.IFileUploadService;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@Service
public class CloudinaryService implements IFileUploadService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile multipartFile) {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(), Collections.emptyMap())
                    .get("url")
                    .toString();
        } catch (IOException e) {
            throw new UploadFileException("Failed to upload image: " + e.getMessage());
        }
    }

}