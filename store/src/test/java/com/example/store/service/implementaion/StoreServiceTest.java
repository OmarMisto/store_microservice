package com.example.store.service.implementaion;

import com.example.store.client.StockFeignClient;
import com.example.store.client.dto.CreateStockResponseDto;
import com.example.store.model.Store;
import com.example.store.model.dto.CreateStoreDto;
import com.example.store.model.dto.CreatedStoreDto;
import com.example.store.repository.StoreRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepo storeRepo;
    @Mock
    private StockFeignClient stockFeignClient;
    @InjectMocks
    private StoreService storeService;

    @Test
    void createStoreService() {
        when(storeRepo.existsByStoreName("Shop")).thenReturn(false);
        Store savedStore= Store.builder()
                .storeId(0)
                .storeName("Shop")
                .city("Damascuse")
                .country("Syria")
                .phoneNumber("99988886666")
                .email("shop@gmail")
                .bio("bio")
                .build();
        when(storeRepo.save(any(Store.class))).thenReturn(savedStore);
        when(stockFeignClient.createStock(0)).thenReturn(CreateStockResponseDto.builder().stockId(19).storeId(0).build());
        CreatedStoreDto createdStoreDto= storeService.createStoreService(CreateStoreDto.builder().storeName("Shop").bio("bio") .city("Damascuse")
                .country("Syria")
                .phoneNumber("99988886666")
                .email("shop@gmail").build());
        assertEquals(0,createdStoreDto.getStoreId());
        Mockito.verify(storeRepo,times(1)).existsByStoreName(anyString());
        Mockito.verify(storeRepo,times(1)).save(any(Store.class));
    }

    @Test
    void gteStoreService() {
    }

    @Test
    void deleteStoreService() {
    }

    @Test
    void updateStoreLogoService() {
    }

    @Test
    void updateStoreDataService() {
    }
}