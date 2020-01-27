package com.war.orke.service;

import com.war.orke.dto.ColonyDto;
import com.war.orke.entity.Colony;
import com.war.orke.jdbcRepository.ColonyJdbcRepository;
import com.war.orke.mapper.ColonyMapper;
import com.war.orke.repository.ColonyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColonyServiceTest {

    private static final int COLONY_LIST_SIZE = 3;
    private static final String COLONY_NAME = "Moscow";
    private static final BigInteger POPULATION_COUNT = BigInteger.valueOf(10);

    @Mock
    private ColonyJdbcRepository colonyJdbcRepository;
    @Mock
    private ColonyRepository colonyRepository;
    @Mock
    private ColonyMapper colonyMapper;

    @InjectMocks
    private ColonyServiceImpl service;

    @Test
    public void testGettingOrkeColonies() {
        when(colonyRepository.findAll()).thenReturn(getColonyIterable());
        when(colonyMapper.toOrkeColonyDto(any(Colony.class)))
                .thenReturn(getColonyDto());

        List<ColonyDto> resultList = service.getOrkeColonies();

        assertAll(
                () -> assertEquals(3, resultList.size()),
                () -> assertEquals(getExpectedColonyListDto(), resultList));
    }

    @Test
    public void testUpdatingColonyInfo() {
        service.updateColonyInfo(COLONY_NAME, POPULATION_COUNT);
        verify(colonyJdbcRepository, times(1)).updateColonyInfo(COLONY_NAME, POPULATION_COUNT);
    }

    @Test
    public void testAddingNewColony() {
        ColonyDto colonyDto = getColonyDto();
        Colony colony = getColony(1, POPULATION_COUNT, COLONY_NAME);

        when(colonyMapper.toOrkeColony(colonyDto)).thenReturn(colony);

        service.addingNewColony(colonyDto);
        verify(colonyRepository, times(1)).save(colony);
    }

    @Test
    public void testDeletingColony() {
        service.deleteColony(COLONY_NAME);
        verify(colonyRepository, times(1)).deleteColonyByName(COLONY_NAME);
    }

    private Iterable<Colony> getColonyIterable() {
        List<Colony> colonyDtos = new ArrayList<>();
        for (int i = 0; i < COLONY_LIST_SIZE; i++) {
            colonyDtos.add(getColony(i+1, BigInteger.valueOf(i * 100), COLONY_NAME + i));
        }
        return colonyDtos;
    }

    private Colony getColony(Integer id, BigInteger population, String name) {
        Colony dto = new Colony();
        dto.setId(id);
        dto.setPopulationCount(population);
        dto.setName(name);
        return dto;
    }

    private ColonyDto getColonyDto() {
        ColonyDto dto = new ColonyDto();
        dto.setId(1);
        dto.setPopulationCount(POPULATION_COUNT);
        dto.setName("AfterMapper");
        return dto;
    }

    private List<ColonyDto> getExpectedColonyListDto() {
        List<ColonyDto> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(getColonyDto());
        }
        return list;
    }
}
