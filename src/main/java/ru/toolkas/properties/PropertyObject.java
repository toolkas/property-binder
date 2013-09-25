package ru.toolkas.properties;

public final class PropertyObject {
    private String originalValue;
    private Object value;
    private String name;
    private Class type;

    public PropertyObject(String name, Class type, String originalValue) {
        this.name = name;
        this.type = type;
        this.originalValue = originalValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        PropertyValueUtils.checkType(type, value);
        this.value = value;
    }
}
