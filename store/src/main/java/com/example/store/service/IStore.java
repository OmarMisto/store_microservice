package com.example.store.service;

import com.example.store.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStore {
    public CreatedStoreDto createStoreService(CreateStoreDto createStoreDto);
    public UpdateLogoDto updateStoreLogoService(MultipartFile logo, long storeId);
    public GetStoreDto gteStoreService(long storeId);
    public String deleteStoreService(long storeId);
    public UpdatedStoreDto updateStoreDataService(long storeId, UpdateStoreDto updateStoreDto);
    public List<FindStoreDto> listStores(String city, int page);
}
