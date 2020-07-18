package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Service;

import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public UserDTO createUser(UserDTO userDetails);

    public UserDTO getUserDetailsByUsername(String username);
}
