package com._up.megastore.unit.custom;

import com._up.megastore.controllers.requests.CreateSizeRequest;
import com._up.megastore.controllers.responses.SizeResponse;
import com._up.megastore.data.model.Size;
import com._up.megastore.data.repositories.ISizeRepository;
import com._up.megastore.services.implementations.SizeService;
import com._up.megastore.services.mappers.SizeMapper;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaveSizeTest {

    @Mock
    private ISizeRepository sizeRepository;

    @Mock
    private Size size;

    @Mock
    private SizeResponse sizeResponse;

    @InjectMocks
    private SizeService sizeService;

    MockedStatic<SizeMapper> mockedSizeMapper;

    @BeforeEach
    void setUp() {
        mockedSizeMapper = mockStatic(SizeMapper.class);
        when(sizeRepository.existsByNameAndDeletedIsFalse(any())).thenAnswer(invocationOnMock ->
                "test".equals(invocationOnMock.getArgument(0)));
    }

    @AfterEach
    void tearDown() {
        mockedSizeMapper.close();
    }

    @Test
    void saveSize_existentName() {
        CreateSizeRequest createSizeRequest = new CreateSizeRequest("test", "");
        assertThrows(ResponseStatusException.class, () -> sizeService.saveSize(createSizeRequest));
    }

    @Test
    void saveSize_nonExistentName() {
        mockSuccessfullSizeSave();

        CreateSizeRequest createSizeRequest = new CreateSizeRequest("test2", "");
        assertDoesNotThrow(() -> sizeService.saveSize(createSizeRequest));
    }

    private void mockSuccessfullSizeSave() {
        mockedSizeMapper.when(() -> SizeMapper.toSize(any())).thenReturn(size);
        mockedSizeMapper.when(() -> SizeMapper.toSizeResponse(any())).thenReturn(sizeResponse);
        when(sizeRepository.save(size)).thenReturn(size);
    }
}
