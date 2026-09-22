package com.example.store.service.exception;

import jakarta.persistence.EntityNotFoundException;

public class StoreNotFoundException extends EntityNotFoundException {
   public StoreNotFoundException(String message){
        super(message);
    }
}
