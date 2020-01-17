package com.war.orke.dto;

public class ErrorDto {

    private String exception;
    private Integer code;

    public ErrorDto(String exception, Integer code) {
        this.exception = exception;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
