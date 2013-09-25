package ru.toolkas.properties.converter;

import ru.toolkas.properties.PropertyValueConverter;

public class StringConverter implements PropertyValueConverter<String> {
    @Override
    public String convert(String value) {
        return value;
    }
}
