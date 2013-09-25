package ru.toolkas.properties.validator;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyObject;
import ru.toolkas.properties.PropertyValidator;
import ru.toolkas.properties.ValidatePropertyException;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

public class NotNullValidator implements PropertyValidator {
    @Override
    public void validate(PropertyObject propertyObject, Map<Class<? extends Annotation>, Annotation> annotations) throws ValidatePropertyException {
        Object value = propertyObject.getValue();
        if (value instanceof String) {
            if (StringUtils.isBlank((String) value)) {
                throw new ValidatePropertyException("Property [" + propertyObject.getName() + "] can not be blank");
            }
        } else if (value instanceof Collection) {
            if (((Collection) value).isEmpty()) {
                throw new ValidatePropertyException("Property [" + propertyObject.getName() + "] can not be empty");
            }
        } else {
            if (value == null) {
                throw new ValidatePropertyException("Property [" + propertyObject.getName() + "] can not be null");
            }
        }
    }
}
