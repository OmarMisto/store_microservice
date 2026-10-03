package com.example.store.service.implementaion;

import com.example.store.model.Store;
import com.example.store.model.StoreLogo;
import com.example.store.model.dto.DBUpdateLogoDto;
import com.example.store.model.dto.UpdateLogoDto;
import com.example.store.repository.StoreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBStoreImage implements IStoreImage{
    private final StoreRepo storeRepo;
    @Override
    public UpdateLogoDto storeImage(Store store) {
        Store savedStore= storeRepo.save(store);
        return DBUpdateLogoDto.builder()
                .logoId(savedStore.getStoreLogo().getStoreLogoId())
                .contentType(savedStore.getStoreLogo().getContentType())
                .name(savedStore.getStoreLogo().getName())
                .size(savedStore.getStoreLogo().getSize())
                .build();
    }
}
