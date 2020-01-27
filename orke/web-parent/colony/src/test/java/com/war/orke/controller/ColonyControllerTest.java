package com.war.orke.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.war.orke.dto.ColonyDto;
import com.war.orke.service.ColonyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ColonyControllerTest {

    private static final String URL_REFIX = "/orke/colony";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String COLONY_NAME = "Moscow";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColonyService colonyService;

    @Test
    public void testGettingColonies() throws Exception {
        ColonyDto colonyDto = getColonyDto();
        when(colonyService.getOrkeColonies()).thenReturn(Collections.singletonList(colonyDto));

        MvcResult mvcResult = mockMvc.perform(get(URL_REFIX + "/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assertions.assertFalse(contentAsString.isEmpty());
        List<ColonyDto> colonyResult = MAPPER.readValue(contentAsString, new TypeReference<List<ColonyDto>>(){});

        assertEquals(colonyDto, colonyResult.get(0));
    }

    @Test
    public void testAddingNewColony() throws Exception {
        ColonyDto dto = getColonyDto();

        mockMvc.perform(post(URL_REFIX + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(colonyService, times(1)).addingNewColony(dto);
    }

    @Test
    public void testUpdatingColonyInfo() throws Exception {
        mockMvc.perform(put(URL_REFIX + "/update/" + COLONY_NAME)
                .contentType(MediaType.APPLICATION_JSON)
                .param("population", BigInteger.TEN.toString()))
                .andExpect(status().isNoContent());

        verify(colonyService, times(1)).updateColonyInfo(COLONY_NAME, BigInteger.TEN);
    }

    @Test
    public void testDeletingColony() throws Exception {
        mockMvc.perform(delete(URL_REFIX + "/delete/" + COLONY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(colonyService, times(1)).deleteColony(COLONY_NAME);
    }

    private ColonyDto getColonyDto() {
        ColonyDto colonyDto = new ColonyDto();
        colonyDto.setName(COLONY_NAME);
        colonyDto.setPopulationCount(BigInteger.TEN);
        return colonyDto;
    }
}