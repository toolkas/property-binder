package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

public class EnumConverter implements PropertyValueConverter<Enum> {
    private final Class<? extends Enum> enumType;

    public EnumConverter(Class<? extends Enum> enumType) {
        this.enumType = enumType;
    }

    @Override
    public Enum convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Enum.valueOf(enumType, value);
        }
        return null;
    }
}
