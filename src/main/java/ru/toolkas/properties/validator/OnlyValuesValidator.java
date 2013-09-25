package ru.toolkas.properties.validator;

import org.apache.commons.lang.ObjectUtils;
import ru.toolkas.properties.PropertyObject;
import ru.toolkas.properties.PropertyValidator;
import ru.toolkas.properties.ValidatePropertyException;
import ru.toolkas.properties.validator.annotations.OnlyStringValues;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;

public class OnlyValuesValidator implements PropertyValidator {
    @Override
    public void validate(PropertyObject object, Map<Class<? extends Annotation>, Annotation> annotations) throws ValidatePropertyException {
        try {
            processOnlyStringValues(object, annotations);
        } catch (IllegalAccessException ex) {
            throw new ValidatePropertyException(ex);
        }
    }

    private void processOnlyStringValues(PropertyObject object, Map<Class<? extends Annotation>, Annotation> annotations) throws IllegalAccessException, ValidatePropertyException {
        OnlyStringValues only = (OnlyStringValues) annotations.get(OnlyStringValues.class);
        if (only != null) {
            String value = (String) object.getValue();

            for (String val : only.value()) {
                if (ObjectUtils.equals(val, value)) {
                    return;
                }
            }

            throw new ValidatePropertyException("Property " + object.getName() + " has incorrect value [" + value + "]. Must be one of " + Arrays.toString(only.value()));
        }
    }
}
