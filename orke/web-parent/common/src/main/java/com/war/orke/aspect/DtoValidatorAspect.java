package com.war.orke.aspect;

import com.war.orke.exception.ValidationException;
import com.war.orke.validator.DtoValidator;
import com.war.orke.validator.ValidatorService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * By using this aspect we can annotate some object or collection of objects by {@link DtoValidator}
 * and add in value our custom validator class, which implemented {@link org.springframework.validation.Validator}.
 * It very useful to control returned object from one API to another and write checks by object's values not in
 * business logic, but in another place named 'validation'.
 *
 * In our project perfect place for this annotating is controllers.
 *
 * @see DtoValidator
 * @see org.springframework.validation.Validator
 * */
@Aspect
@Component
public class DtoValidatorAspect {

    @Autowired
    private ValidatorService validatorService;

    @Around("execution(* com.war.orke.controller..*.*(.., @com.war.orke.validator.DtoValidator (*),..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        int exceptionHandlerIndex = getExceptionHandlerIndex(parameters);

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            DtoValidator declaredAnnotation = parameter.getDeclaredAnnotation(DtoValidator.class);
            if (Objects.nonNull(declaredAnnotation)) {
                try {
                    validatorService.validate(args[i], declaredAnnotation.value());
                } catch (ValidationException e) {
                    if (exceptionHandlerIndex != -1) {
                        args[exceptionHandlerIndex] = e;
                    } else {
                        throw e;
                    }
                }
            }
        }
        return point.proceed();
    }

    private int getExceptionHandlerIndex(Parameter[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (ValidationException.class.isAssignableFrom(parameter.getType())) {
                return i;
            }
        }
        return -1;
    }
}
