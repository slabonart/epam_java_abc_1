package com.epam.jmp.service.impl;

public class SubscriptionNotFoundException extends RuntimeException {

    private static final String MESSAGE = "No subscription found for card number: ";

    public SubscriptionNotFoundException(String message) {
        super(MESSAGE + message);
    }
}
