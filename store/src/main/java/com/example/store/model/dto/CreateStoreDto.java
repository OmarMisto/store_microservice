package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreateStoreDto {
    private String storeName;
    private String city;
    private String country;
    private String bio;
    private String email;
    private String phoneNumber;
}
