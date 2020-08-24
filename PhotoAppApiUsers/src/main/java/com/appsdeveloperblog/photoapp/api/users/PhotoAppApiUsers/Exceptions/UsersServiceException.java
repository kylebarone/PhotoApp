package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Exceptions;

public class UsersServiceException extends RuntimeException{

    private String userId;
    ErrorMessage errorMessage;

    public UsersServiceException(String userId) {
        super();
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "UsersNotFoundException{\n" +
                "\tUser you are trying to access does not exist. \n "
                + "\tuserId='" + userId + '\'' +
                "\n}";
    }
}
