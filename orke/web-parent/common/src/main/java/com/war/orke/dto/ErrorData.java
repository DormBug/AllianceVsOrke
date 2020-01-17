package com.war.orke.dto;

public class ErrorData {

    private String field;
    private String errorMessage;

    public ErrorData(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "field '" + field + "' " + "error message '" + errorMessage + "'";
    }
}
