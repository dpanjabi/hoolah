package com.hoolah.util;

public class DataLoadingException extends RuntimeException {
    public DataLoadingException(String msg) {
        super(msg);
    }

    public DataLoadingException(String msg, Throwable t) {
        super(msg, t);
    }
}
