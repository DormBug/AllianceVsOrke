package com.war.alliance.domain;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface EnumType<T> {

    static <E extends Enum<E> & EnumType<T>, T> Optional<E> findOptionalEnumValue(Class<E> enumClass, T value) {
        return Stream.of(enumClass.getEnumConstants())
                .filter(e -> Objects.equals(e.getValue(), value))
                .findFirst();
    }

    T getValue();

    default boolean equalsTo(T value) {
        return getValue().equals(value);
    }

}
