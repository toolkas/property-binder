package ru.toolkas.properties;

public class BindPropertyException extends Exception {
    public BindPropertyException() {
    }

    public BindPropertyException(String message) {
        super(message);
    }

    public BindPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindPropertyException(Throwable cause) {
        super(cause);
    }
}
