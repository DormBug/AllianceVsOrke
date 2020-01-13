package com.war.alliance.unit;

import com.war.alliance.domain.EnumType;
import com.war.alliance.domain.IntegerEnumType;

public enum UnitLevel implements IntegerEnumType {

    RECRUIT(1),
    EXPERIENCED(2),
    ELITE(3);

    private Integer level;

    UnitLevel(Integer level) {
        this.level = level;
    }

    public Integer getValue() {
        return level;
    }

    public static UnitLevel of(Integer value) {
        return EnumType.findOptionalEnumValue(UnitLevel.class, value).orElse(null);
    }
}
