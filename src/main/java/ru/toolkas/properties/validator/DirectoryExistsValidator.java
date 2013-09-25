package ru.toolkas.properties.validator;

import ru.toolkas.properties.PropertyObject;
import ru.toolkas.properties.PropertyValidator;
import ru.toolkas.properties.ValidatePropertyException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Map;

public class DirectoryExistsValidator implements PropertyValidator {
    @Override
    public void validate(PropertyObject object, Map<Class<? extends Annotation>, Annotation> annotations) throws ValidatePropertyException {
        String value = String.valueOf(object.getValue());
        File file = new File(value);
        if (!file.exists()) {
            throw new ValidatePropertyException("File [" + value + "] in property [" + object.getName() + "] does not exist");
        }

        if (!file.isDirectory()) {
            throw new ValidatePropertyException("File [" + value + "] in property [" + object.getName() + "] is not directory");
        }
    }
}
