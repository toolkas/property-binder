package ru.toolkas.properties;

import java.lang.reflect.Method;
import java.util.Properties;

public interface DelegateHandler {
    void init(final Properties properties, Method method);
}
