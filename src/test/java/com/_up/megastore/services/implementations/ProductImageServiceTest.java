package com._up.megastore.services.implementations;

import com._up.megastore.data.model.ProductImage;
import com._up.megastore.data.repositories.IProductImageRepository;
import com._up.megastore.services.interfaces.IFileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceTest {

    @Mock
    private IProductImageRepository productImageRepository;

    @Mock
    private IFileUploadService fileUploadService;

    @InjectMocks
    private ProductImageService productImageService;

    private final MultipartFile mockedImage = mock(MultipartFile.class);
    private final MultipartFile[] mockedImagesArray = { mockedImage };
    private final ProductImage productImage = new ProductImage();

    @Nested
    class ImageSizeUnitTests {

        @Test
        void saveProductImage_sizeIsLessThan1MB() {
            when(fileUploadService.uploadImage(any(MultipartFile.class))).thenReturn("Test url");
            when(mockedImage.getOriginalFilename()).thenReturn("Test image");
            when(productImageRepository.save(Mockito.any(ProductImage.class))).thenReturn(productImage);
            when(mockedImage.getSize()).thenReturn(999_999L);

            assertDoesNotThrow(() -> productImageService.saveProductImages(mockedImagesArray));
        }

        @Test
        void saveProductImage_sizeIsBiggerThan1MB() {
            when(mockedImage.getSize()).thenReturn(1_000_001L);
            assertThrows(ResponseStatusException.class, () -> productImageService.saveProductImages(mockedImagesArray));
        }
    }

    @Nested
    class ImageFilenameUnitTests {

        @BeforeEach
        void setUp() {
            when(mockedImage.getSize()).thenReturn(999_999L);
            when(productImageRepository.existsByName(anyString())).thenAnswer(invocation ->
                    "Nike Shirt".equals(invocation.getArgument(0)));
        }

        @Test
        void saveProductImage_filenameIsLessThanOriginalName() {
            when(fileUploadService.uploadImage(any(MultipartFile.class))).thenReturn("Test url");
            when(productImageRepository.save(Mockito.any(ProductImage.class))).thenReturn(productImage);
            when(mockedImage.getOriginalFilename()).thenReturn("Nike Shir");

            assertDoesNotThrow(() -> productImageService.saveProductImages(mockedImagesArray));
        }

        @Test
        void saveProductImage_filenameIsMoreThanOriginalName() {
            when(fileUploadService.uploadImage(any(MultipartFile.class))).thenReturn("Test url");
            when(productImageRepository.save(Mockito.any(ProductImage.class))).thenReturn(productImage);
            when(mockedImage.getOriginalFilename()).thenReturn("Nike Shirtt");

            assertDoesNotThrow(() -> productImageService.saveProductImages(mockedImagesArray));
        }

        @Test
        void saveProductImage_filenameExists() {
            when(mockedImage.getOriginalFilename()).thenReturn("Nike Shirt");
            assertThrows(ResponseStatusException.class, () -> productImageService.saveProductImages(mockedImagesArray));
        }
    }

}