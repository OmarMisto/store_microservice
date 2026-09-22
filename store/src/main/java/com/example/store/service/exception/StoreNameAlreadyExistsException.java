package com.example.store.service.exception;

public class StoreNameAlreadyExistsException extends RuntimeException{
    public StoreNameAlreadyExistsException(String message){
        super(message);
    }
}
