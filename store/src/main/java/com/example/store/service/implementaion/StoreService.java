package com.example.store.service.implementaion;

import com.example.store.model.dto.*;
import com.example.store.model.Store;
import com.example.store.model.StoreLogo;
import com.example.store.repository.StoreRepo;
import com.example.store.service.IStore;
import com.example.store.service.exception.ImageSizeException;
import com.example.store.service.exception.StoreNameAlreadyExistsException;
import com.example.store.service.exception.StoreNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StoreService implements IStore {
    private final StoreRepo storeRepo;
    public StoreService(StoreRepo storeRepo){
        this.storeRepo=storeRepo;
    }
    @Override
    @Transactional
    public CreatedStoreDto createStoreService(MultipartFile logo, CreateStoreDto createStoreDto) {
            if (logo.getSize() > 10485760) {
                throw new ImageSizeException("image size should be less then or equals 5 MB");
            }
            if (storeRepo.existsByStoreName(createStoreDto.getStoreName())){
                throw new StoreNameAlreadyExistsException("the store name: "+ createStoreDto.getStoreName() +" is already in use try an other store name");
            }
            try {
                Store store = storeRepo.save(Store.builder()
                        .storeLogo(StoreLogo.builder().logoBytes(logo.getBytes()).contentType(logo.getContentType()).name(logo.getName()).size(logo.getSize()).build())
                        .storeName(createStoreDto.getStoreName())
                        .bio(createStoreDto.getBio())
                        .city(createStoreDto.getCity())
                        .country(createStoreDto.getCountry())
                        .email(createStoreDto.getEmail())
                        .phoneNumber(createStoreDto.getPhoneNumber())
                        .build());
                return CreatedStoreDto.builder().storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .bio(store.getBio())
                        .city(store.getCity())
                        .country(store.getCountry())
                        .email(store.getEmail())
                        .logo(store.getStoreLogo().getLogoBytes())
                        .phoneNumber(store.getPhoneNumber())
                        .build();

            }catch (IOException exception){
                throw new RuntimeException("failed to create a store "+ exception.getMessage());
            }
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
    public UpdateLogoDto updateStoreLogoService(long storeId, MultipartFile logo) {
        try {
            Store store =storeRepo.findById(storeId).orElseThrow(()->new StoreNotFoundException("Not Found"));
            store.setStoreLogo(StoreLogo.builder().storeLogoId(store.getStoreLogo().getStoreLogoId()).logoBytes(logo.getBytes()).size(logo.getSize()).name(logo.getName()).contentType(logo.getContentType()).build());
            StoreLogo storeLogo= storeRepo.save(store).getStoreLogo();
            return UpdateLogoDto.builder().bytes(storeLogo.getLogoBytes())
                    .contentType(storeLogo.getContentType())
                    .storeId(store.getStoreId())
                    .name(storeLogo.getName())
                    .size(storeLogo.getSize())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
