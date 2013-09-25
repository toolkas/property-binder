package ru.toolkas.properties;

import org.junit.Assert;
import org.junit.Test;
import ru.toolkas.properties.annotations.Property;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Properties;

public class PropertyDoubleConvertionTest {
    private static final String KEY = "key";

    private final Properties properties = new Properties();

    @Test
    public void testStringToIntProperty() throws BindPropertyException {
        try {
            properties.clear();
            properties.setProperty(KEY, "TEST");

            Example example = PropertyBinder.forType(Example.class).bind(properties);
            example.getValue();
        } catch (Throwable th) {
            if (th instanceof UndeclaredThrowableException) {
                th = ((UndeclaredThrowableException) th).getUndeclaredThrowable();
            }
            Assert.assertTrue(th instanceof BindPropertyException);
        }
    }

    public static interface Example {
        @Property(KEY)
        double getValue();
    }
}
