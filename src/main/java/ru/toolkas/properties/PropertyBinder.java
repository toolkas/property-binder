package ru.toolkas.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class PropertyBinder<T> {
    private final Class<T> iClass;
    private boolean fromXML = false;

    public PropertyBinder(Class<T> iClass) {
        this.iClass = iClass;
    }

    public static <U> PropertyBinder<U> forType(Class<U> iClass) {
        return new PropertyBinder<U>(iClass);
    }

    public PropertyBinder<T> fromXML() {
        fromXML = true;
        return this;
    }

    public T bind(final File file) throws BindPropertyException, IOException {
        InputStream input = null;
        try {
            input = new FileInputStream(file);
            return bind(input);
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public T bind(final InputStream input) throws BindPropertyException, IOException {
        Properties properties = new Properties();
        if (fromXML) {
            properties.loadFromXML(input);
        } else {
            properties.load(input);
        }
        return bind(properties);
    }

    public T bind(final Properties properties) throws BindPropertyException {
        try {
            InvocationHandler handler = new PropertyBinderInvocationHandler(iClass, properties);
            return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{iClass}, handler);
        } catch (Exception ex) {
            if (ex instanceof BindPropertyException) {
                throw (BindPropertyException) ex;
            }
            throw new BindPropertyException(ex);
        }
    }
}
