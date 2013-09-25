package ru.toolkas.properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.toolkas.properties.annotations.Property;

import java.io.File;
import java.util.*;

/**
 * Тесты, для проверки корректности @Property
 * при отсутствии значений в Properties
 */
public class PropertyMissingValuesTest {
    private static final String STRING_KEY = "stringKey";
    private static final String INT_KEY = "intKey";
    private static final String DOUBLE_KEY = "doubleKey";
    private static final String BOOLEAN_KEY = "booleanKey";
    private static final String COLLECTION_KEY = "collectionKey";
    private static final String FILE_KEY = "fileKey";

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
    public void testCollectionProperty() {
        Assert.assertEquals(example.getListValue(), new ArrayList());
        Assert.assertEquals(example.getSetValue(), new HashSet());
    }

    @Test
    public void testFileProperty() {
        Assert.assertTrue(example.getFileValue() == null);
    }

    public static interface Example {
        @Property(INT_KEY)
        int getIntValue();

        @Property(INT_KEY)
        Integer getIntValue2();

        @Property(DOUBLE_KEY)
        double getDoubleValue();

        @Property(DOUBLE_KEY)
        Double getDoubleValue2();

        @Property(BOOLEAN_KEY)
        boolean getBooleanValue();

        @Property(BOOLEAN_KEY)
        Boolean getBooleanValue2();

        @Property(STRING_KEY)
        String getStringValue();

        @Property(COLLECTION_KEY)
        List getListValue();

        @Property(COLLECTION_KEY)
        Set getSetValue();

        @Property(FILE_KEY)
        File getFileValue();
    }
}
