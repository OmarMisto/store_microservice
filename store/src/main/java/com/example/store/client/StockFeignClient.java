package com.example.store.client;

import com.example.store.client.dto.CreateStockResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "stock-service",url = "/api/v1/stock/")
public interface StockFeignClient {
    @PostMapping("create/{storeId}")
    public CreateStockResponseDto createStock(@PathVariable("storeId")long storeId);
}
