package com._up.megastore.unit.custom;

import com._up.megastore.controllers.requests.CreateCategoryRequest;
import com._up.megastore.controllers.responses.CategoryResponse;
import com._up.megastore.data.model.Category;
import com._up.megastore.data.repositories.ICategoryRepository;
import com._up.megastore.services.implementations.CategoryService;
import com._up.megastore.services.mappers.CategoryMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveCategoryTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private Category category;

    @Mock
    private CategoryResponse categoryResponse;

    @InjectMocks
    private CategoryService categoryService;

    MockedStatic<CategoryMapper> mockedCategoryMapper;

    @BeforeEach
    void setUp() {
        mockedCategoryMapper = mockStatic(CategoryMapper.class);
        when(categoryRepository.existsByNameAndDeletedIsFalse(anyString())).thenAnswer(invocationOnMock ->
                "test".equals(invocationOnMock.getArgument(0)));
    }

    @AfterEach
    void tearDown() {
        mockedCategoryMapper.close();
    }

    @Test
    void saveCategory_existentName() {
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("test", "", null);
        assertThrows(ResponseStatusException.class, () -> categoryService.saveCategory(createCategoryRequest));
    }

    @Test
    void saveCategory_nonExistentName() {
        mockSuccessfullCategorySave();

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest("test2", "", null);
        assertDoesNotThrow(() -> categoryService.saveCategory(createCategoryRequest));
    }

    private void mockSuccessfullCategorySave() {
        mockedCategoryMapper.when(() -> CategoryMapper.toCategory(any(), any())).thenReturn(category);
        mockedCategoryMapper.when(() -> CategoryMapper.toCategoryResponse(any())).thenReturn(categoryResponse);
        when(categoryRepository.save(category)).thenReturn(category);
    }

}
