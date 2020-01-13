package com.war.alliance.converter;

import com.war.alliance.converter.domain.AbstractIntegerEnumConverter;
import com.war.alliance.unit.UnitLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UnitLevelConverter extends AbstractIntegerEnumConverter<UnitLevel>
        implements AttributeConverter<UnitLevel, Integer> {
}
