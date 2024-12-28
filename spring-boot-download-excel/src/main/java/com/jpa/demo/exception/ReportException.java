package com.jpa.demo.exception;

public class ReportException extends RuntimeException{
    public ReportException(String msgError) {
        super(msgError);
    }
}
