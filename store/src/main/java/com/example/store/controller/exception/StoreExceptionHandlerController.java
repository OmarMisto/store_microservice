package com.example.store.controller.exception;

import com.example.store.model.dto.ErrorMessageResponse;
import com.example.store.service.exception.ImageSizeException;
import com.example.store.service.exception.StoreNameAlreadyExistsException;
import com.example.store.service.exception.StoreNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class StoreExceptionHandlerController {
    @ExceptionHandler(ImageSizeException.class)
    public ResponseEntity<ErrorMessageResponse>handleImageSizeException(ImageSizeException e){
        return ResponseEntity.status(400)
                .body(ErrorMessageResponse.builder()
                        .status(400)
                        .error("Bad Request")
                        .message(e.getMessage())
                        .time(LocalDateTime.now())
                        .build());
    }
    @ExceptionHandler(StoreNameAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse>handleStoreNameAlreadyExistException(StoreNameAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorMessageResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .error("Conflict")
                        .message(e.getMessage())
                        .time(LocalDateTime.now())
                        .build());
    }
    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse>handleEntityNotFoundException(StoreNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .time(LocalDateTime.now())
                        .message(e.getMessage())
                        .error("Not Found")
                .build());
    }
}
