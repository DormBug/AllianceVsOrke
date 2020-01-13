package com.war.alliance.construction;

import com.war.alliance.domain.EnumType;
import com.war.alliance.domain.IntegerEnumType;

public enum BuildingLevel implements IntegerEnumType {

    NEWLY_BUILT(1),
    FORTIFIED(2),
    IMPENETRABLE(3);

    private Integer level;

    BuildingLevel(Integer level) {
        this.level = level;
    }

    public Integer getValue() {
        return level;
    }

    public static BuildingLevel of(Integer value) {
        return EnumType.findOptionalEnumValue(BuildingLevel.class, value).orElse(null);
    }
}
