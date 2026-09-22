package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateStoreDto  {
    private String  storeName;
    private String  city;
    private String  country;
    private String  bio;
    private String  email;
    private String  phoneNumber;

}
