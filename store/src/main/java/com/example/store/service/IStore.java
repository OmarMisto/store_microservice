package com.example.store.service;

import com.example.store.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

public interface IStore {
    public CreatedStoreDto createStoreService(MultipartFile logo, CreateStoreDto createStoreDto);
    public GetStoreDto gteStoreService(long storeId);
    public String deleteStoreService(long storeId);
    public UpdateLogoDto updateStoreLogoService(long storeId,MultipartFile logo);
    public UpdatedStoreDto updateStoreDataService(long storeId, UpdateStoreDto updateStoreDto);
    public List<FindStoreDto> listStores(String city, int page);
}
