package ru.toolkas.properties.processor;

import org.apache.commons.lang.StringUtils;
import ru.toolkas.properties.PropertyProcessor;
import ru.toolkas.properties.PropertyObject;
import ru.toolkas.properties.processor.annotations.DefaultBooleanValue;
import ru.toolkas.properties.processor.annotations.DefaultEnumConstant;
import ru.toolkas.properties.processor.annotations.DefaultIntValue;
import ru.toolkas.properties.processor.annotations.DefaultStringValue;

import java.lang.annotation.Annotation;
import java.util.Map;

public class DefaultValueProcessor implements PropertyProcessor {
    @Override
    public void process(PropertyObject object, Map<Class<? extends Annotation>, Annotation> annotations) {
        if (StringUtils.isBlank(object.getOriginalValue())) {
            DefaultStringValue defaultStringValue = (DefaultStringValue) annotations.get(DefaultStringValue.class);

            if (defaultStringValue != null) {
                if (object.getType() != String.class) {
                    throw new IllegalStateException("annotation " + DefaultStringValue.class + " is not applicable for property of type " + object.getType());
                }

                object.setValue(defaultStringValue.value());
                return;
            }

            DefaultIntValue defaultIntValue = (DefaultIntValue) annotations.get(DefaultIntValue.class);
            if (defaultIntValue != null) {
                if (object.getType() != int.class && object.getType() != Integer.class) {
                    throw new IllegalStateException("annotation " + DefaultIntValue.class + " is not applicable for property of type " + object.getType());
                }

                object.setValue(defaultIntValue.value());
                return;
            }

            DefaultBooleanValue defaultBooleanValue = (DefaultBooleanValue) annotations.get(DefaultBooleanValue.class);
            if (defaultBooleanValue != null) {
                if (object.getType() != boolean.class && object.getType() != Boolean.class) {
                    throw new IllegalStateException("annotation " + DefaultBooleanValue.class + " is not applicable for property of type " + object.getType());
                }

                object.setValue(defaultBooleanValue.value());
                return;
            }

            DefaultEnumConstant defaultEnumConstant = (DefaultEnumConstant) annotations.get(DefaultEnumConstant.class);
            if (defaultEnumConstant != null) {
                if (!object.getType().isEnum()) {
                    throw new IllegalStateException("annotation " + DefaultEnumConstant.class + " is not applicable for property of type " + object.getType());
                }

                object.setValue(Enum.valueOf(object.getType(), defaultEnumConstant.value()));
                return;
            }
        }
    }
}
