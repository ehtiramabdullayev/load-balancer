package org.example.exception;

public class NoProviderRegisteredException extends Throwable {
    public NoProviderRegisteredException() {
    }

    public NoProviderRegisteredException(String message) {
        super(message);
    }
}