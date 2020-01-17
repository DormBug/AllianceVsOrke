package com.war.orke.service;

import com.war.orke.dto.ColonyDto;
import com.war.orke.mapper.ColonyMapper;
import com.war.orke.jdbcRepository.ColonyJdbcRepository;
import com.war.orke.repository.ColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrkeColonyServiceImpl implements OrkeColonyService {

    @Autowired
    private ColonyJdbcRepository colonyJdbcRepository;
    @Autowired
    private ColonyRepository colonyRepository;
    @Autowired
    private ColonyMapper colonyMapper;

    @Override
    public List<ColonyDto> getOrkeColonies() {
        return StreamSupport.stream(colonyRepository.findAll().spliterator(), false)
                .map(x -> colonyMapper.toOrkeColonyDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public void updateColonyInfo(String colonyName, BigInteger population) {
        colonyJdbcRepository.updateColonyInfo(colonyName, population);
    }

    @Transactional
    @Override
    public void addingNewColony(ColonyDto colonyDto) {
        colonyRepository.save(colonyMapper.toOrkeColony(colonyDto));
    }
}
