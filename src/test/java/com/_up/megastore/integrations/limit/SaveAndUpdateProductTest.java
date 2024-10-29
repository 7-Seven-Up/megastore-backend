package com._up.megastore.integrations.limit;

import com._up.megastore.cloudinary.CloudinaryService;
import com._up.megastore.controllers.requests.CreateProductRequest;
import com._up.megastore.controllers.requests.UpdateProductRequest;
import com._up.megastore.integrations.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/scripts/products/save_and_update_products.sql")
public class SaveAndUpdateProductTest extends BaseIntegrationTest {

    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void saveProduct_nameIsLessThanRequired() throws Exception {
        CreateProductRequest request = new CreateProductRequest(
                "Re",
                "Product description",
                1000.0,
                10,
                "#ffffff",
                UUID.fromString("03f667f7-4075-41f1-a35d-b4dc71f05b8a"),
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile createProductRequest = new MockMultipartFile(
                "createProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products")
                                .file(createProductRequest)
                                .file(image)
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product name must be between 3 and 30 characters");
    }

    @Test
    void saveProduct_nameIsMoreThanRequired() throws Exception {
        CreateProductRequest request = new CreateProductRequest(
                "Remera Osborne Paris Hilton VIP",
                "Product description",
                1000.0,
                10,
                "#ffffff",
                UUID.fromString("03f667f7-4075-41f1-a35d-b4dc71f05b8a"),
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile createProductRequest = new MockMultipartFile(
                "createProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products")
                                .file(createProductRequest)
                                .file(image)
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product name must be between 3 and 30 characters");
    }

    @Test
    void saveProduct_nameIsCorrect() throws Exception {
        when(cloudinaryService.uploadImage(any())).thenReturn(UUID.randomUUID().toString());

        CreateProductRequest request = new CreateProductRequest(
                "Remera Osborne",
                "Product description",
                1000.0,
                10,
                "#ffffff",
                UUID.fromString("03f667f7-4075-41f1-a35d-b4dc71f05b8a"),
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile createProductRequest = new MockMultipartFile(
                "createProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products")
                                .file(createProductRequest)
                                .file(image)
                ).andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void saveProduct_negativePrice() throws Exception {
        CreateProductRequest request = new CreateProductRequest(
                "Product Name",
                "Product description",
                -1.0,
                10,
                "#ffffff",
                UUID.fromString("03f667f7-4075-41f1-a35d-b4dc71f05b8a"),
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile createProductRequest = new MockMultipartFile(
                "createProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products")
                                .file(createProductRequest)
                                .file(image)
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Price must be 0 or positive");
    }

    @Test
    void saveProduct_positivePrice() throws Exception {
        when(cloudinaryService.uploadImage(any())).thenReturn(UUID.randomUUID().toString());

        CreateProductRequest request = new CreateProductRequest(
                "Product Name",
                "Product description",
                1.0,
                10,
                "#ffffff",
                UUID.fromString("03f667f7-4075-41f1-a35d-b4dc71f05b8a"),
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile createProductRequest = new MockMultipartFile(
                "createProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products")
                                .file(createProductRequest)
                                .file(image)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.price").value(request.price()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void updateProduct_nameIsLessThanRequired() throws Exception {
        UpdateProductRequest request = new UpdateProductRequest(
                "Re",
                "Product description",
                1000.0,
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile updateProductRequest = new MockMultipartFile(
                "updateProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                                .file(updateProductRequest)
                                .file(image)
                                .with(mockedRequest -> {
                                    mockedRequest.setMethod("PATCH");
                                    return mockedRequest;
                                })
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product name must be between 3 and 30 characters");
    }

    @Test
    void updateProduct_nameIsMoreThanRequired() throws Exception {
        UpdateProductRequest request = new UpdateProductRequest(
                "Remera Osborne Paris Hilton VIP",
                "Product description",
                1000.0,
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile updateProductRequest = new MockMultipartFile(
                "updateProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                                .file(updateProductRequest)
                                .file(image)
                                .with(mockedRequest -> {
                                    mockedRequest.setMethod("PATCH");
                                    return mockedRequest;
                                })
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Product name must be between 3 and 30 characters");
    }

    @Test
    void updateProduct_nameIsCorrect() throws Exception {
        when(cloudinaryService.uploadImage(any())).thenReturn(UUID.randomUUID().toString());

        UpdateProductRequest request = new UpdateProductRequest(
                "Remera Osborne",
                "Product description",
                1000.0,
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile updateProductRequest = new MockMultipartFile(
                "updateProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                                .file(updateProductRequest)
                                .file(image)
                                .with(mockedRequest -> {
                                    mockedRequest.setMethod("PATCH");
                                    return mockedRequest;
                                })
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }

    @Test
    void updateProduct_negativePrice() throws Exception {
        UpdateProductRequest request = new UpdateProductRequest(
                "Product name",
                "Product description",
                -1.0,
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile updateProductRequest = new MockMultipartFile(
                "updateProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                                .file(updateProductRequest)
                                .file(image)
                                .with(mockedRequest -> {
                                    mockedRequest.setMethod("PATCH");
                                    return mockedRequest;
                                })
                ).andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "message", "Price must be 0 or positive");
    }

    @Test
    void updateProduct_positivePrice() throws Exception {
        when(cloudinaryService.uploadImage(any())).thenReturn(UUID.randomUUID().toString());

        UpdateProductRequest request = new UpdateProductRequest(
                "Product name",
                "Product description",
                1.0,
                UUID.fromString("0bbad5ac-3986-4832-8a87-0141a83052ce"),
                null
        );

        String jsonRequest = toJson(request);

        MockMultipartFile image = new MockMultipartFile(
                "multipartFiles",
                "test-image1.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test imagen".getBytes()
        );

        MockMultipartFile updateProductRequest = new MockMultipartFile(
                "updateProductRequest",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                jsonRequest.getBytes()
        );

        String response = mockMvc.perform(
                        multipart("/api/v1/products/183f205a-3430-4e11-8bca-57672a0ce3ff")
                                .file(updateProductRequest)
                                .file(image)
                                .with(mockedRequest -> {
                                    mockedRequest.setMethod("PATCH");
                                    return mockedRequest;
                                })
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertContains(response, "name", request.name());
        assertContains(response, "description", request.description());
    }
}