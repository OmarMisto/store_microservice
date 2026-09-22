package com.example.store.model.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ErrorMessageResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime time;

}
