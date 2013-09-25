package ru.toolkas.properties;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface PropertyValidator {
    void validate(final PropertyObject object, final Map<Class<? extends Annotation>, Annotation> annotations) throws ValidatePropertyException;
}
