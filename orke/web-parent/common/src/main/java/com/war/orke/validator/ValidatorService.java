package com.war.orke.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Collection;

@Service
public class ValidatorService {

    @Autowired
    private ApplicationContext applicationContext;

    public void validate(Object object, Class<? extends Validator> validator) {
        if (DefaultValidator.class.equals(validator)) {
            return;
        }
        if (object instanceof Collection) {
           validateCollection((Collection)object, validator);
        }
        validateCommon(object, validator);
    }

    private void validateCollection(Collection<Object> collection, Class<? extends Validator> validator) {
        Validator validatorClazz = applicationContext.getBean(validator);
        collection.forEach(object -> {
            BindingResult bindingResult = new BeanPropertyBindingResult(
                    object, StringUtils.uncapitalize(object.getClass().getSimpleName()));
            ValidationUtils.invokeValidator(validatorClazz, object, bindingResult);
        });
    }

    private void validateCommon(Object object, Class<? extends Validator> validator) {
        BindingResult bindingResult = new BeanPropertyBindingResult(
                object, StringUtils.uncapitalize(object.getClass().getSimpleName()));
        Validator validatorClazz = applicationContext.getBean(validator);
        ValidationUtils.invokeValidator(validatorClazz, object, bindingResult);
    }
}
