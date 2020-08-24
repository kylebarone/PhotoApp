package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Service;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data.UserRepository;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Exceptions.*;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.FakeDataBase;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.CreateUserRequest;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserRequestUpdate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{


    UserRepository userRepository;
    RestTemplate restTemplate;
    Environment env;

    BCryptPasswordEncoder bCryptPasswordEncoder;
    Logger logger = LoggerFactory.getLogger(this.getClass());;

    @Autowired
    public UserServiceImpl(Environment env, UserRepository userRepository,
                           RestTemplate restTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.restTemplate = restTemplate;
    }


    @Override
    public UserDTO getUserDetailsByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsersServiceException(userId);
        }

        UserDTO userDTO = new ModelMapper().map(userEntity, UserDTO.class);

        String url = String.format(env.getProperty("api.albums-ws.endpoints.getAlbums.url"), userDTO.getUserId());
        logger.info("!!!!!Making API call to " + url + " !!!!");
        ResponseEntity<List<AlbumResponseModel>> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AlbumResponseModel>>(){});
        logger.info("!!!!!API call was made!!!!");

        userDTO.setAlbums(responseEntity.getBody());
        return userDTO;
    }


    @Override
    public UserDTO getUserDetailsByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new ModelMapper().map(userEntity, UserDTO.class);
    }


    @Override
    public UserDTO createUser(UserDTO userDetails) {

        String userId = UUID.randomUUID().toString();
        userDetails.setUserId(userId);
        userDetails.setEncryptedPass(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        userRepository.save(userEntity);
        UserDTO returnUserDetails = modelMapper.map(userEntity, UserDTO.class);

        return returnUserDetails;
    }


    // since we use user details service to authenticate password we need a way for it to acess db for this authentivation and thats where this method comes into play
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);

        // this comes from the userDetails interface
        return new User(userEntity.getUsername(), userEntity.getEncryptedPass(),
                true, true, true, true, new ArrayList<>());
    }


}
