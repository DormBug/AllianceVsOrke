package com.war.orke.exception;

import com.war.orke.dto.ErrorData;

import java.util.Collections;
import java.util.List;

public class ValidationException extends RuntimeException {

    private List<ErrorData> errors;

    public ValidationException(List<ErrorData> errors) {
        this.errors = errors;
    }

    public ValidationException(ErrorData error) {
        this.errors = Collections.singletonList(error);
    }

    public List<ErrorData> getErrors() {
        return errors;
    }
}
