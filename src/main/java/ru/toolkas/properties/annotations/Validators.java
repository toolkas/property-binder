package ru.toolkas.properties.annotations;

import ru.toolkas.properties.PropertyValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validators {
    Class<? extends PropertyValidator>[] value();
}
