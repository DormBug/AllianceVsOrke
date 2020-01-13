package com.war.alliance.converter.domain;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class Utils {

    private Utils() {
        super();
    }

    public static <T> Class<T> getGenericParamClass(Class<?> genericClass) {
        ParameterizedType type = getConverterGenericSuperclass(genericClass);
        Type[] args = type.getActualTypeArguments();
        if (args.length != 0) {
            return (Class<T>) type.getActualTypeArguments()[0];
        }
        throw new IllegalArgumentException(String.format("Class [%s] should extend parent with defined generic types.",
                genericClass.getSimpleName()));
    }

    private static ParameterizedType getConverterGenericSuperclass(Class<?> genericClass) {
        Type superclass = genericClass.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            return (ParameterizedType) superclass;
        }
        throw new IllegalArgumentException(String.format("Class [%s] should extend parent with defined generic types.",
                genericClass.getSimpleName()));
    }

}
