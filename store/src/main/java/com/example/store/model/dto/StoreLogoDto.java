package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class StoreLogoDto {
    private byte[] bytes;
    private String name;
    private String contentType;
    private long size;
}
