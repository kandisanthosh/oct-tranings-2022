package com.javainuse.controllers;

public class RetryException extends Exception {

    private String message;

    public RetryException(String message) {
        this.message = message;
    }
}
