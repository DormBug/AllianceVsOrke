package com.war.orke.handler;

import com.war.orke.dto.ErrorDto;
import com.war.orke.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonWebExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CommonWebExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorDto> invalidWebData(MethodArgumentNotValidException exception) {
        LOG.trace(exception.toString());
        return new ResponseEntity<>(
                new ErrorDto("Invalid data was entered", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseEntity<ErrorDto> failedValidation(ValidationException exception) {
        LOG.trace(exception.toString());
        StringBuilder builder = new StringBuilder();
        exception.getErrors().forEach(x -> builder.append(x.toString()));
        return new ResponseEntity<>(new ErrorDto(builder.toString(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

}
