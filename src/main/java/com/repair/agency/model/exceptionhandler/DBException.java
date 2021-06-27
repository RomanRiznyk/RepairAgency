package com.repair.agency.model.exceptionhandler;

import java.sql.SQLException;

public class DBException extends SQLException {
    private String message;
    private Exception cause;

    public DBException() {
    }

    public DBException(String message, Exception cause) {
        super();
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Exception getCause() {
        return cause;
    }
}
