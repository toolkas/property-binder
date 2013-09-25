package ru.toolkas.properties;

public class PropertyValueUtils {
    private PropertyValueUtils() {
    }

    public static void checkType(Class type, Object value) {
        if (value == null) {
            if (type.isPrimitive()) {
                throw new IllegalArgumentException("Value '" + value + "' can't have a null value");
            }
        } else {
            Class valueType = value.getClass();
            Class propertyType = type;

            if (propertyType.isPrimitive()) {
                propertyType = org.apache.commons.lang.ClassUtils.primitiveToWrapper(propertyType);
            }
            if (valueType.isPrimitive()) {
                valueType = org.apache.commons.lang.ClassUtils.primitiveToWrapper(valueType);
            }

            if (valueType != propertyType && !propertyType.isAssignableFrom(valueType)) {
                throw new IllegalArgumentException("value type " + valueType + " and property type " + propertyType + " are not compatible");
            }
        }
    }
}
