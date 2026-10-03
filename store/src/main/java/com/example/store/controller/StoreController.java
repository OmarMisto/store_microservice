package com.example.store.controller;

import com.example.store.model.dto.CreateStoreDto;
import com.example.store.model.dto.CreatedStoreDto;
import com.example.store.model.dto.UpdateStoreDto;
import com.example.store.service.IStore;
import com.example.store.service.implementaion.StoreService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1/store/")
public class StoreController {
    private final IStore iStore;
    public StoreController(StoreService storeService){
        this.iStore=  storeService;
    }
    @PostMapping("create/")
    public ResponseEntity<CreatedStoreDto>createStoreController(@RequestBody CreateStoreDto createStoreDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(iStore.createStoreService(createStoreDto));
    }
    @PatchMapping("update/logo/{storeId}")
    public ResponseEntity<?>updateStoreLogo(@RequestPart(name = "logo") MultipartFile logo, @PathVariable("storeId") long storeId){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iStore.updateStoreLogoService(logo,storeId));
    }
    @DeleteMapping("delete/{storeId}")
    public ResponseEntity<?>deleteStoreController(@PathVariable(name = "storeId")long storeId){
        return ResponseEntity.ok(iStore.deleteStoreService(storeId));
    }
    @GetMapping("get/{storeId}")
    public ResponseEntity<?>getStoreByIdController(@PathVariable(name = "storeId")long storeId){
        return ResponseEntity.ok(iStore.gteStoreService(storeId));
    }
    @PatchMapping ("update/{storeId}/data")
    public ResponseEntity<?>updateStoreFieldController(@PathVariable(name = "storeId")long storeId, @RequestBody UpdateStoreDto updateStoreDto){
        return ResponseEntity.ok(iStore.updateStoreDataService(storeId,updateStoreDto));
    }
    @GetMapping("get/stores/{city}")
    public ResponseEntity<?>getStoreByCityController(@PathVariable("city")String city, @PathVariable("page") int page){
        return ResponseEntity.ok(iStore.listStores(city, page));
    }
}
