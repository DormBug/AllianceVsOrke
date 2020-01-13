package com.war.alliance.converter.domain;

import com.war.alliance.domain.StringEnumType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractStringEnumConverter<X extends StringEnumType> implements AttributeConverter<X, String> {

    @Override
    public String convertToDatabaseColumn(X attribute) {
        return Objects.nonNull(attribute) ? attribute.getValue() : null;
    }

    @Override
    public X convertToEntityAttribute(String dbData) {
        Class<X> classType = Utils.getGenericParamClass(this.getClass());
        Optional<X> optionalValue = Optional.ofNullable(dbData)
                .map(s -> " ".equals(s) ? s : s.trim())
                .flatMap(s -> Stream.of(classType.getEnumConstants())
                        .filter(e -> e.getValue().equals(s))
                        .findFirst());
        if (!optionalValue.isPresent() && StringUtils.isBlank(dbData)) {
            return null;
        }
        return optionalValue.orElseThrow(() -> new IllegalArgumentException(
                String.format("Unable to find enum [%s] with value [%s]",
                        classType.getName(), Objects.requireNonNull(dbData).trim())));
    }


}
