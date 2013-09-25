package ru.toolkas.properties;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.toolkas.properties.annotations.Property;

import java.io.File;
import java.util.*;

/**
 * Тесты, для проверки корректности @Property
 * При использовании конвертеров по умолчанию
 */
public class PropertyDefaultConvertersTest {
    private static final String STRING_KEY = "stringKey";
    private static final String STRING_VALUE = "stringValue";

    private static final String INT_KEY = "intKey";
    private static final int INT_VALUE = 15;

    private static final String DOUBLE_KEY = "doubleKey";
    private static final double DOUBLE_VALUE = 15.134;

    private static final String BOOLEAN_KEY = "booleanKey";
    private static final boolean BOOLEAN_VALUE = true;

    private static final String COLLECTION_KEY = "collectionKey";
    private static final List COLLECTION_VALUE = Arrays.asList("a", "b", "c");

    private static final String FILE_KEY = "fileKey";
    private static final File FILE_VALUE = new File("TESTTEST");

    private final Properties properties = new Properties();
    private Example example;

    @Before
    public void setUp() throws Exception {
        properties.setProperty(STRING_KEY, STRING_VALUE);
        properties.setProperty(INT_KEY, String.valueOf(INT_VALUE));
        properties.setProperty(DOUBLE_KEY, String.valueOf(DOUBLE_VALUE));
        properties.setProperty(BOOLEAN_KEY, String.valueOf(BOOLEAN_VALUE));
        properties.setProperty(COLLECTION_KEY, StringUtils.join(COLLECTION_VALUE, ";"));
        properties.setProperty(FILE_KEY, FILE_VALUE.getPath());

        example = PropertyBinder.forType(Example.class).bind(properties);
    }

    @Test
    public void testStringProperty() {
        Assert.assertTrue(STRING_VALUE.equals(example.getStringValue()));
    }

    @Test
    public void testIntProperty() {
        Assert.assertTrue(INT_VALUE == example.getIntValue());
        Assert.assertTrue(new Integer(INT_VALUE).equals(example.getIntValue2()));
    }

    @Test
    public void testDoubleProperty() {
        Assert.assertTrue(DOUBLE_VALUE == example.getDoubleValue());
        Assert.assertTrue(new Double(DOUBLE_VALUE).equals(example.getDoubleValue2()));
    }

    @Test
    public void testBooleanProperty() {
        Assert.assertTrue(BOOLEAN_VALUE == example.getBooleanValue());
        Assert.assertTrue(new Boolean(BOOLEAN_VALUE).equals(example.getBooleanValue2()));
    }

    @Test
    public void testCollectionProperty() {
        Assert.assertTrue(COLLECTION_VALUE.equals(example.getListValue()));
        Assert.assertTrue(new HashSet(COLLECTION_VALUE).equals(example.getSetValue()));
    }

    @Test
    public void testFileProperty() {
        Assert.assertTrue(FILE_VALUE.equals(example.getFileValue()));
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
