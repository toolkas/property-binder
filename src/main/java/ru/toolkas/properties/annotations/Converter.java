package ru.toolkas.properties.annotations;

import ru.toolkas.properties.PropertyValueConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Converter {
    Class<? extends PropertyValueConverter> value();
}
