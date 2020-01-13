package com.war.orke.service;

import com.war.orke.dto.ColonyDto;

import java.math.BigInteger;
import java.util.List;

public interface OrkeColonyService {

    List<ColonyDto> getOrkeColonies();

    void updateColonyInfo(String colonyName, BigInteger population);

    void addingNewColony(ColonyDto colonyDto);
}
