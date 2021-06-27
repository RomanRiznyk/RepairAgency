package com.repair.agency.model.exceptionhandler;

public class DBWasChangedException extends DBException{
    String message;
    Exception cause;

    public DBWasChangedException(String message) {
        super();
        this.message = message;
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
