package com._up.megastore.unit.custom;

import com._up.megastore.data.model.Category;
import com._up.megastore.data.repositories.ICategoryRepository;
import com._up.megastore.services.implementations.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private Category category;

    @Mock
    private Category subcategory;

    @InjectMocks
    private CategoryService categoryService;

    private final UUID predefinedUUID = UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b");

    @BeforeEach
    void setUp() {
        when(categoryRepository.findById(any(UUID.class))).thenAnswer(invocationOnMock ->
                predefinedUUID.equals(invocationOnMock.getArgument(0))
                        ? Optional.of(category)
                        : Optional.empty()
        );
    }

    @Test
    void deleteCategory_incorrectUUID() {
        assertThrows(ResponseStatusException.class, () ->
                categoryService.deleteCategory(UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836c")));
    }

    @Test
    void deleteCategory_categoryHasSubcategories() {
        when(category.getSubCategories()).thenReturn(List.of(subcategory));
        assertThrows(ResponseStatusException.class, () -> categoryService.deleteCategory(predefinedUUID));
    }

    @Test
    void deleteCategory_categoryIsAlreadyDeleted() {
        when(category.getSubCategories()).thenReturn(Collections.emptyList());
        when(category.isDeleted()).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> categoryService.deleteCategory(predefinedUUID));
    }

    @Test
    void deleteCategory_categoryDoesNotHaveSubcategoriesAndIsNotDeleted() {
        when(categoryRepository.findById(predefinedUUID)).thenReturn(Optional.of(category));
        when(category.getSubCategories()).thenReturn(Collections.emptyList());
        when(category.isDeleted()).thenReturn(false);

        assertDoesNotThrow(() -> categoryService.deleteCategory(predefinedUUID));
    }

}
