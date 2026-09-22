package com.example.store.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateLogoDto {
    private byte[] bytes;
    private String contentType;
    private String name;
    private long size;
    private long storeId;
}
