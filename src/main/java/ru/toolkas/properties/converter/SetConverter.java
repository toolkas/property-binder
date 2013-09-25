package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetConverter implements PropertyValueConverter<Set> {
    @Override
    public Set convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return new HashSet(Arrays.asList(value.split(";")));
        }
        return new HashSet();
    }
}
