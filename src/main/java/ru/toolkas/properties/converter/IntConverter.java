package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

public class IntConverter implements PropertyValueConverter<Integer> {
    @Override
    public Integer convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }
}
