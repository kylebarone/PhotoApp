package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull(message = "Name cant be null")
    @Size(min=2, message = "First name must be atleast 2 characters")
    private String name;
    @NotNull(message = "Username cant be null")
    @Size(min=4, message = "Username must be atleast 4 characters")
    private String username;
    @NotNull(message = "Email cant be null")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Password cant be null")
    @Size(min=6, message = "Password must be atleast 6 characters")
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
