package ru.toolkas.properties;

public interface PropertyValueConverter<T> {
    T convert(String value);
}
