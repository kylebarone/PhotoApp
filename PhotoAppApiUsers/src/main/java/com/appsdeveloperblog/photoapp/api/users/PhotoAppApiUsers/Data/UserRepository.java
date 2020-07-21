package com.appsdeveloperblog.photoapp.api.users.PhotoAppApiUsers.Data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    UserEntity findByUserId(String userId);
}
