package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class FindStoreDto {
    private long storeId;
    private String storeName;
    private String bio;
    private String email;
    private String phoneNumber;
    private String city;
    private String country;
    private StoreLogoDto storeLogoDto;
}
