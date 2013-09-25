package ru.toolkas.properties.converter;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyValueConverter;

import java.io.File;

public class FileConverter implements PropertyValueConverter<File> {
    @Override
    public File convert(String value) {
        if (StringUtils.isNotBlank(value)) {
            return new File(value);
        }
        return null;
    }
}
