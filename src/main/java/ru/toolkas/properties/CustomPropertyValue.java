package ru.toolkas.properties;

import java.lang.reflect.Method;
import java.util.Properties;

public interface CustomPropertyValue<T> {
    void init(final Properties properties, Method method);
    T get(Properties properties, Method method);
}
