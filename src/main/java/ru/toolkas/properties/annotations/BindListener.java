package ru.toolkas.properties.annotations;

import ru.toolkas.properties.PropertyBindListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindListener {
    Class<? extends PropertyBindListener>[] value();
}
