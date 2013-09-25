package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

public class DoubleConverter implements PropertyValueConverter<Double> {
    @Override
    public Double convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Double.parseDouble(value);
        }
        return 0d;
    }
}
