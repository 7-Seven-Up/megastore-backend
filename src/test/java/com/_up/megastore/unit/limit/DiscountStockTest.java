package com._up.megastore.unit.limit;

import com._up.megastore.data.model.Product;
import com._up.megastore.data.repositories.IProductRepository;
import com._up.megastore.services.implementations.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountStockTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private Product product;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        when(product.getStock()).thenReturn(20);
    }

    @Test
    void discountProductStock_stockRequestedIsSmaller() {
        when(productRepository.save(product)).thenReturn(product);
        assertDoesNotThrow(() -> productService.discountProductStock(19, product));
    }

    @Test
    void discountProductStock_stockRequestedIsBigger() {
        assertThrows(ResponseStatusException.class, () -> productService.discountProductStock(21, product));
    }
}
