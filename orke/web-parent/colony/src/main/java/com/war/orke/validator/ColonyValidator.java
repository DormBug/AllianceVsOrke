package com.war.orke.validator;

import com.war.orke.dto.ColonyDto;
import com.war.orke.dto.ErrorData;
import com.war.orke.exception.ValidationException;
import com.war.orke.repository.ColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ColonyValidator implements Validator {

    private static final String FIELD_NAME = "name";
    private static final String EXISTING_COLONY_NAME_ERROR_MESSAGE = "Colony with name '%s' is already created";

    @Autowired
    private ColonyRepository colonyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ColonyDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ColonyDto colony = (ColonyDto) target;
        // some business logic
        String name = colony.getName();
        if (colonyRepository.findByName(name).isPresent()) {
            throw new ValidationException(
                    new ErrorData(FIELD_NAME, String.format(EXISTING_COLONY_NAME_ERROR_MESSAGE, name)));
        }
    }
}
