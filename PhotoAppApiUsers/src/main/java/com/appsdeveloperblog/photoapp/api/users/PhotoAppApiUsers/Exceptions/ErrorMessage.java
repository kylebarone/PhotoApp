package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Exceptions;

public class ErrorMessage {
    String message;
    String stackTrace;

    public ErrorMessage(String message, String stackTrace) {
        this.message = message;
        this.stackTrace = stackTrace;
    }

    public ErrorMessage(String stackTrace) {
        this.message = null;
        this.stackTrace = stackTrace;
    }

    public ErrorMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}