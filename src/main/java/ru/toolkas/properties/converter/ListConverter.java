package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListConverter implements PropertyValueConverter<List> {
    @Override
    public List convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Arrays.asList(value.split(";"));
        }
        return new ArrayList();
    }
}
