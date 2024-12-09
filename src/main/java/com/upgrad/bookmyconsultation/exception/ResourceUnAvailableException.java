package com.upgrad.bookmyconsultation.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceUnAvailableException extends RuntimeException{
    public ResourceUnAvailableException(String message){
        super(message);
    }
}
