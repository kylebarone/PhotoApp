package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Service;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data.UserRepository;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Exceptions.*;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.FakeDataBase;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserDTO;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.CreateUserRequest;
import com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Model.UserRequestUpdate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FakeDataBase<String, UserDTO> map;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDTO getUserData(String userId) {
        if (map.containsKey(userId)) {
            return map.get(userId);
        }  else {
            throw new EmptyDataException();
        }
    }

    public UserDTO createUser(UserDTO userDetails) {

        String userId = UUID.randomUUID().toString();
        userDetails.setUserId(userId);
        userDetails.setEncryptedPass(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userRepository.save(userEntity);
        UserDTO returnUserDetails = modelMapper.map(userEntity, UserDTO.class);

        return returnUserDetails;
    }


    public UserDTO updateUserData(String userId, UserRequestUpdate userRequest) {
        if (!map.containsKey(userId)) {
            throw new EmptyDataException();
        }
        UserDTO user = map.get(userId);
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        map.put(userId, user);
        return user;
    }


    public UserDTO deleteUser(String userId) {
        if (!map.containsKey(userId)) {
            throw new EmptyDataException();
        }
        return map.remove(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getUsername(), userEntity.getEncryptedPass(),
                true, true, true, true, new ArrayList<>());
    }
}
