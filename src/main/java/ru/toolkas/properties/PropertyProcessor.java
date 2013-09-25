package ru.toolkas.properties;

import java.lang.annotation.Annotation;
import java.util.Map;

public interface PropertyProcessor {
    void process(PropertyObject object,  final Map<Class<? extends Annotation>, Annotation> annotations);
}
