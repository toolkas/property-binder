package ru.toolkas.properties;

import org.apache.commons.lang.ClassUtils;
import ru.toolkas.properties.annotations.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class PropertyBinderInvocationHandler implements InvocationHandler {
    private final Properties properties;

    public PropertyBinderInvocationHandler(Class iClass, Properties properties) throws BindPropertyException {
        this.properties = properties;

        fireBindListeners(properties, iClass);
    }

    private void fireBindListeners(Properties properties, Class iClass) throws BindPropertyException {
        try {
            BindListener bindListener = (BindListener) iClass.getAnnotation(BindListener.class);
            if (bindListener != null) {
                for (Class<? extends PropertyBindListener> clazz : bindListener.value()) {
                    PropertyBindListener listener = clazz.newInstance();
                    listener.onInit(properties);
                }
            }
        } catch (Exception ex) {
            throw new BindPropertyException(ex);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class returnType = method.getReturnType();

        //Обычное свойство
        try {
            Property property = method.getAnnotation(Property.class);
            if (property != null) {
                return getPropertyValue(method, property, returnType);
            }

            //Сложное свойство
            CustomProperty customProperty = method.getAnnotation(CustomProperty.class);
            if (customProperty != null) {
                return getCustomPropertyValue(method, customProperty, returnType);
            }

            //Делегат
            Delegate delegate = method.getAnnotation(Delegate.class);
            if (delegate != null) {
                DelegateHandler handler = getDelegateHandler(method, delegate);
                Method m = handler.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
                return m.invoke(handler, args);
            }
        } catch (Exception ex) {
            if (ex instanceof BindPropertyException) {
                throw ex;
            }
            throw new BindPropertyException("Can't get method " + method + " value", ex);
        }

        return getDefaultValue(method.getReturnType());
    }

    private Object getPropertyValue(Method method, Property property, Class returnType) throws InstantiationException, IllegalAccessException, ValidatePropertyException, BindPropertyException {
        Object value = null;
        if (returnType != Void.TYPE) {
            String stringValue = properties.getProperty(property.value());
            if (stringValue != null) {
                stringValue = stringValue.trim();
            }
            final PropertyObject propertyObject = new PropertyObject(property.value(), returnType, stringValue);
            PropertyValueConverter valueConverter = getValueConverter(method, returnType);
            if (valueConverter == null) {
                throw new BindPropertyException("value '" + stringValue + "' of property '" + property.value() + "' can't be converted to " + returnType);
            }

            value = valueConverter.convert(stringValue);

            propertyObject.setValue(value);
            final Map<Class<? extends Annotation>, Annotation> annotations = getAnnotations(method);

            processProperty(propertyObject, annotations);
            validateProperty(propertyObject, annotations);

            value = propertyObject.getValue();
        }

        return value;
    }

    private Object getCustomPropertyValue(Method method, CustomProperty customProperty, Class returnType) throws IllegalAccessException, InstantiationException {
        CustomPropertyValue propertyValue = customProperty.value().newInstance();
        propertyValue.init(properties, method);
        Object value = propertyValue.get(properties, method);
        PropertyValueUtils.checkType(returnType, value);
        return value;
    }

    private DelegateHandler getDelegateHandler(Method method, Delegate delegate) throws IllegalAccessException, InstantiationException, BindPropertyException, IOException {
        Class<? extends DelegateHandler> clazz = delegate.value();

        Method delegateMethod = null;
        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(method.getName()) && m.getReturnType().equals(method.getReturnType()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())) {
                delegateMethod = m;
                break;
            }
        }

        if (delegateMethod == null) {
            throw new BindPropertyException("there is no appropriate method-handler in " + clazz + " for method " + method);
        }

        DelegateHandler handler = clazz.newInstance();
        handler.init(properties, method);
        return handler;
    }

    private void validateProperty(PropertyObject propertyObject, Map<Class<? extends Annotation>, Annotation> annotations) throws InstantiationException, IllegalAccessException, ValidatePropertyException {
        Validators validators = (Validators) annotations.get(Validators.class);
        if (validators != null && validators.value() != null) {
            for (Class<? extends PropertyValidator> clazz : validators.value()) {
                PropertyValidator validator = clazz.newInstance();
                validator.validate(propertyObject, annotations);
            }
        }
    }

    private void processProperty(PropertyObject propertyObject, Map<Class<? extends Annotation>, Annotation> annotations) throws InstantiationException, IllegalAccessException {
        Processors processors = (Processors) annotations.get(Processors.class);
        if (processors != null && processors.value() != null) {
            for (Class<? extends PropertyProcessor> clazz : processors.value()) {
                PropertyProcessor processor = clazz.newInstance();
                processor.process(propertyObject, annotations);
            }
        }
    }

    private Map<Class<? extends Annotation>, Annotation> getAnnotations(Method method) {
        final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();
        if (method.getAnnotations() != null) {
            for (Annotation annotation : method.getAnnotations()) {
                annotations.put(annotation.annotationType(), annotation);
            }
        }
        return Collections.unmodifiableMap(annotations);
    }

    private PropertyValueConverter getValueConverter(Method method, Class returnType) throws InstantiationException, IllegalAccessException {
        Converter converter = method.getAnnotation(Converter.class);
        PropertyValueConverter valueConverter = null;
        if (converter != null) {
            valueConverter = converter.value().newInstance();
        } else {
            valueConverter = DefaultConverters.getConverterForType(returnType);
        }
        return valueConverter;
    }

    private Object getDefaultValue(Class type) {
        if (type.isPrimitive()) {
            type = ClassUtils.primitiveToWrapper(type);
        }

        if (Integer.class == type) {
            return 0;
        }

        if (Double.class == type) {
            return 0d;
        }

        if (Boolean.class == type) {
            return false;
        }
        return null;
    }
}
