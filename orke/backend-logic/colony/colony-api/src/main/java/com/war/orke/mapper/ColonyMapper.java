package com.war.orke.mapper;

import com.war.orke.dto.ColonyDto;
import com.war.orke.entity.Colony;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColonyMapper {

    ColonyDto toOrkeColonyDto(Colony colony);

    Colony toOrkeColony(ColonyDto colonyDto);

}
