package com.example.store.client.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreateStockResponseDto {
    private long stockId;
    private long storeId;
}
