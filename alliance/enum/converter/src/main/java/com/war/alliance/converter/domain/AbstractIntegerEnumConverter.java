package com.war.alliance.converter.domain;

import com.war.alliance.domain.IntegerEnumType;

import javax.persistence.AttributeConverter;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractIntegerEnumConverter<X extends IntegerEnumType>
        implements AttributeConverter<X, Integer> {

    @Override
    public Integer convertToDatabaseColumn(X attribute) {
        return Objects.nonNull(attribute) ? attribute.getValue() : null;
    }

    @Override
    public X convertToEntityAttribute(Integer dbData) {
        if (Objects.isNull(dbData)) {
            return null;
        }
        Class<X> type = Utils.getGenericParamClass(this.getClass());
        return Stream.of(type.getEnumConstants())
                .filter(e -> e.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unable ti find enum [%s] with value [%s]",
                        type.getName(), dbData)));
    }
}
