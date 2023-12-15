package com.example.springapi.exception;

public class VerificationException extends RuntimeException{
    public VerificationException(String message) {
        super(message);
    }
}
