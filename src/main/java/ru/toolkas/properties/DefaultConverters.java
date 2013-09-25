package ru.toolkas.properties;

import ru.toolkas.properties.converter.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultConverters {
    private static final Map<Class, PropertyValueConverter> converters = new HashMap<Class, PropertyValueConverter>();

    static {
        //базовые типы
        converters.put(int.class, new IntConverter());
        converters.put(Integer.class, new IntConverter());
        converters.put(double.class, new DoubleConverter());
        converters.put(Double.class, new DoubleConverter());
        converters.put(boolean.class, new BooleanConverter());
        converters.put(Boolean.class, new BooleanConverter());
        converters.put(String.class, new StringConverter());

        //коллекции
        converters.put(List.class, new ListConverter());
        converters.put(Set.class, new SetConverter());

        //доп. типы
        converters.put(File.class, new FileConverter());
    }

    private DefaultConverters() {
    }

    public static PropertyValueConverter getConverterForType(Class type) {
        PropertyValueConverter converter = converters.get(type);
        if (converter == null) {
            if (type.isEnum()) {
                return new EnumConverter(type);
            }
        }
        return converter;
    }
}
