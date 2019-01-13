package com.ralph.pmgr.exception;

/**
 * This exception is thrown to signal an error on the server. Sometimes the original cause of the error is also
 * transmitted and can be obtained by calling the getCause method (otherwise getCause will
 * return null).
 */
public class ClientException extends Exception {
    private static final long serialVersionUID = 4697548022257085636L;

    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}