package com.war.orke.controller;

import com.war.orke.dto.ColonyDto;
import com.war.orke.service.ColonyService;
import com.war.orke.validator.ColonyValidator;
import com.war.orke.validator.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/orke/colony")
public class ColonyController {

    @Autowired
    private ColonyService colonyService;

    @GetMapping("/all")
    public List<ColonyDto> getColonies() {
        return colonyService.getOrkeColonies();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addingNewColony(@RequestBody @Valid @DtoValidator(ColonyValidator.class) ColonyDto colonyDto) {
        colonyService.addingNewColony(colonyDto);
    }

    @PutMapping("/update/{colonyName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateColonyInfo(@PathVariable("colonyName") String colonyName,
                                 @RequestParam("population") BigInteger population) {
        colonyService.updateColonyInfo(colonyName, population);
    }

    @DeleteMapping("/delete/{colonyName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteColony(@PathVariable(name = "colonyName") String colonyName) {
        colonyService.deleteColony(colonyName);
    }
}
