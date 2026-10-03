package com.example.store.service.implementaion;

import com.example.store.client.StockFeignClient;
import com.example.store.client.dto.CreateStockResponseDto;
import com.example.store.model.StoreLogo;
import com.example.store.model.dto.*;
import com.example.store.model.Store;
import com.example.store.repository.StoreRepo;
import com.example.store.service.IStore;
import com.example.store.service.exception.StoreNameAlreadyExistsException;
import com.example.store.service.exception.StoreNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService implements IStore {
    private final StoreRepo storeRepo;
    private final StockFeignClient stockFeignClient;
    private final IStoreImage iStoreImage;
    @Override
    @Transactional(rollbackFor = FeignException.FeignClientException.class)
    public CreatedStoreDto createStoreService( CreateStoreDto createStoreDto) {
          //image size with valid and DTO
            if (storeRepo.existsByStoreName(createStoreDto.getStoreName())){
                throw new StoreNameAlreadyExistsException("the store name: "+ createStoreDto.getStoreName() +" is already in use try an other store name");
            }
            try {
                Store store = storeRepo.save(Store.builder()
                        .storeName(createStoreDto.getStoreName())
                        .bio(createStoreDto.getBio())
                        .city(createStoreDto.getCity())
                        .country(createStoreDto.getCountry())
                        .email(createStoreDto.getEmail())
                        .phoneNumber(createStoreDto.getPhoneNumber())
                        .build());
                CreateStockResponseDto createStockResponseDto= stockFeignClient.createStock(store.getStoreId());
                return CreatedStoreDto.builder()
                        .storeId(store.getStoreId())
                        .stockId(createStockResponseDto.getStockId())
                        .storeName(store.getStoreName())
                        .bio(store.getBio())
                        .city(store.getCity())
                        .country(store.getCountry())
                        .email(store.getEmail())
                        .phoneNumber(store.getPhoneNumber())
                        .build();
            }catch (Exception exception){
                throw new RuntimeException("failed to create a store "+ exception.getMessage());
            }
    }

    @Override
    public UpdateLogoDto updateStoreLogoService(MultipartFile logo, long storeId) {
        Store store =storeRepo.findById(storeId).orElseThrow(()->new StoreNotFoundException("Store Not Found failed to update the logo"));
        try {
            store.setStoreLogo(StoreLogo.builder()
                    .logoBytes(logo.getBytes())
                    .contentType(logo.getContentType())
                    .size(logo.getSize())
                    .name(logo.getName())
                    .build());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
       return iStoreImage.storeImage(store);
    }

    @Override
    @Transactional(readOnly = true)
    public GetStoreDto gteStoreService(long storeId) {
            Store store= storeRepo.findById(storeId).orElseThrow(()->new StoreNotFoundException("invalid store id"));
            return GetStoreDto.builder().storeId(store.getStoreId())
                    .storeName(store.getStoreName())
                    .bio(store.getBio())
                    .city(store.getCity())
                    .country(store.getCountry())
                    .email(store.getEmail())
                    .phoneNumber(store.getPhoneNumber())
                    .logo(store.getStoreLogo().getLogoBytes())
                    .build();
    }
    @Override
    @Transactional
    public String deleteStoreService(long storeId) {
        try {
            if (storeRepo.existsById(storeId)){
                  storeRepo.deleteById(storeId);
                  return "success";
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
        throw new StoreNotFoundException("store not found");
    }

    @Override
    @Transactional
    public UpdatedStoreDto updateStoreDataService(long storeId, UpdateStoreDto updateStoreDto) {
        Store store =storeRepo.findById(storeId).orElseThrow(()->new StoreNotFoundException("Not Found"));
        store.setStoreName(updateStoreDto.getStoreName());
        store.setBio(updateStoreDto.getBio());
        store.setCity(updateStoreDto.getCity());
        store.setCountry(updateStoreDto.getCountry());
        store.setEmail(updateStoreDto.getEmail());
        store.setPhoneNumber(updateStoreDto.getPhoneNumber());
        Store savedStore= storeRepo.save(store);
        return UpdatedStoreDto.builder().storeName(savedStore.getStoreName())
                .bio(savedStore.getBio())
                .country(savedStore.getCountry())
                .city(savedStore.getCity())
                .email(savedStore.getEmail())
                .phoneNumber(savedStore.getPhoneNumber())
                .logo(savedStore.getStoreLogo().getLogoBytes())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FindStoreDto> listStores(String city, int page) {
        Pageable pageable = PageRequest.of(page,50);
       return storeRepo.findAllByCity(city,pageable)
               .stream()
               .map((s)->FindStoreDto.builder()
                       .storeId(s.getStoreId())
                       .storeName(s.getStoreName())
                       .email(s.getEmail())
                       .phoneNumber(s.getPhoneNumber())
                       .country(s.getCountry())
                       .city(s.getCity())
                       .bio(s.getBio())
                       .storeLogoDto(StoreLogoDto.builder().bytes(s.getStoreLogo().getLogoBytes()).contentType(s.getStoreLogo().getContentType()).name(s.getStoreLogo().getName()).size(s.getStoreLogo().getSize()).build())
                       .build()).toList();
    }
}
