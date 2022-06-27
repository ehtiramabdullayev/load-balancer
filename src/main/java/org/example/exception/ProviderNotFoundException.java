package org.example.exception;

import java.util.function.Supplier;

public class ProviderNotFoundException extends Throwable {
    public ProviderNotFoundException() {
    }

    public ProviderNotFoundException(String message) {
        super(message);
    }
}