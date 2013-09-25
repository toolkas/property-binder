package ru.toolkas.properties;

public class ValidatePropertyException extends BindPropertyException {
    public ValidatePropertyException() {
    }

    public ValidatePropertyException(String message) {
        super(message);
    }

    public ValidatePropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatePropertyException(Throwable cause) {
        super(cause);
    }
}
