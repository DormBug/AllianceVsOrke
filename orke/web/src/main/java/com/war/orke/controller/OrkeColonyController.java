package com.war.orke.controller;

import com.war.orke.dto.ColonyDto;
import com.war.orke.service.OrkeColonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/orke/colony")
public class OrkeColonyController {

    @Autowired
    private OrkeColonyService orkeColonyService;

    @GetMapping("/all")
    public List<ColonyDto> getColonies() {
        return orkeColonyService.getOrkeColonies();
    }

    @PutMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addingNewColony(@RequestBody ColonyDto colonyDto) {
        orkeColonyService.addingNewColony(colonyDto);
    }

    @PutMapping("/update/{colonyName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateColonyInfo(@PathVariable("colonyName") String colonyName,
                                 @RequestParam("population") BigInteger population) {
        System.out.println(colonyName);
        orkeColonyService.updateColonyInfo(colonyName, population);
    }
}
