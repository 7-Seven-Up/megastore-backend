package com._up.megastore.unit.custom;

import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.ISizeRepository;
import com._up.megastore.services.implementations.SizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteSizeTest {

    @Mock
    private ISizeRepository sizeRepository;

    @Mock
    private Size size;

    @InjectMocks
    private SizeService sizeService;

    private final UUID predefinedId = UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836b");

    @BeforeEach
    void setUp() {
        when(sizeRepository.findById(any(UUID.class))).thenAnswer(invocationOnMock ->
                predefinedId.equals(invocationOnMock.getArgument(0))
                        ? Optional.of(size)
                        : Optional.empty()
        );
    }

    @Test
    void deleteSize_incorrectUUID() {
        assertThrows(ResponseStatusException.class, () ->
                sizeService.deleteSize(UUID.fromString("58fae25b-ea38-4e7b-ab2d-9f555a67836c")));
    }

    @Test
    void deleteSize_sizeIsAlreadyDeleted() {
        when(size.isDeleted()).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> sizeService.deleteSize(predefinedId));
    }

    @Test
    void deleteSize_sizeIsNotDeleted() {
        when(size.isDeleted()).thenReturn(false);
        assertDoesNotThrow(() -> sizeService.deleteSize(predefinedId));
    }
}
