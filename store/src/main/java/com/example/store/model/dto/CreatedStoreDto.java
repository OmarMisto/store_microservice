package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreatedStoreDto {
    private long storeId;
    private byte[] logo;
    private String storeName;
    private String city;
    private String country;
    private String bio;
    private String email;
    private String phoneNumber;

}
