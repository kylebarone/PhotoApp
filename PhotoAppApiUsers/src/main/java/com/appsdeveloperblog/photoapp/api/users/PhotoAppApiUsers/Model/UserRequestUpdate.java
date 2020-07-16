package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model;

import javax.validation.constraints.NotNull;

public class UserRequestUpdate {
    @NotNull(message = "name cant be null")
    private String name;
    @NotNull(message = "username cant be null")
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
