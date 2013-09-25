package ru.toolkas.properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Тесты, для проверки корректности возвращаемых значений
 * неаннотированными методами
 */
public class PlainMethodValuesTest {
    private final Properties properties = new Properties();
    private Example example;

    @Before
    public void setUp() throws Exception {
        example = PropertyBinder.forType(Example.class).bind(properties);
    }

    @Test
    public void testStringProperty() {
        Assert.assertTrue(example.getStringValue() == null);
    }

    @Test
    public void testIntProperty() {
        Assert.assertTrue(example.getIntValue() == 0);
        Assert.assertEquals(example.getIntValue2(), 0);
    }

    @Test
    public void testDoubleProperty() {
        Assert.assertTrue(example.getDoubleValue() == 0d);
        Assert.assertEquals(example.getDoubleValue2(), 0d);
    }

    @Test
    public void testBooleanProperty() {
        Assert.assertFalse(example.getBooleanValue());
        Assert.assertFalse(example.getBooleanValue2());
    }

    @Test
    public void testObject() {
        Assert.assertEquals(example.getObject(), null);
    }

    public static interface Example {
        int getIntValue();

        Integer getIntValue2();

        double getDoubleValue();

        Double getDoubleValue2();

        boolean getBooleanValue();

        Boolean getBooleanValue2();

        String getStringValue();

        Object getObject();
    }
}
