package ru.toolkas.properties.converter;

import ru.toolkas.properties.PropertyValueConverter;

public class BooleanConverter implements PropertyValueConverter<Boolean> {
    @Override
    public Boolean convert(String value) {
        return Boolean.parseBoolean(value);
    }
}
