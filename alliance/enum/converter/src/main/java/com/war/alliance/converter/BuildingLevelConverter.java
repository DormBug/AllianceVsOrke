package com.war.alliance.converter;

import com.war.alliance.construction.BuildingLevel;
import com.war.alliance.converter.domain.AbstractIntegerEnumConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BuildingLevelConverter extends AbstractIntegerEnumConverter<BuildingLevel>
        implements AttributeConverter<BuildingLevel, Integer> {
}
