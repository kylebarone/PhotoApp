package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Controllers;

import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.LoginUserRequest;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.CreateUserRequest;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserRequestUpdate;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserResponse;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    UserServiceImpl userService;
    Environment env;

    @Autowired
    public UserController(UserServiceImpl userService, Environment environment) {
        this.userService = userService;
        this.env = environment;
    }


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDTO userDTO = modelMapper.map(userRequest, UserDTO.class);
        UserDTO returnUserDTO = userService.createUser(userDTO);
        UserResponse response = modelMapper.map(returnUserDTO, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }






    //gotta go back and redo the below shit with what I know now

    @GetMapping(path="/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserData(@PathVariable String userId) {

        UserDTO returnUser = userService.getUserData(userId);
        return new ResponseEntity<>(returnUser, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<UserDTO> updateUserData(@PathVariable String userId,
                                                  @Valid @RequestBody UserRequestUpdate userRequest) {
        UserDTO returnUser = userService.updateUserData(userId, userRequest);
        return new ResponseEntity<>(returnUser, HttpStatus.OK);
    }


    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String userId) {
        UserDTO returnUser = userService.deleteUser(userId);
        return new ResponseEntity<>(returnUser, HttpStatus.OK);
    }


    @GetMapping(path = "/status")
    public ResponseEntity<String> getStatus() {
        String statusMessage = "Working on port: " + env.getProperty("local.server.port") + " ->" + env.getProperty("test.example")
                + "  " + env.getProperty("test.exampleJr");
        return new ResponseEntity<>(statusMessage,
                HttpStatus.OK);
    }

}
