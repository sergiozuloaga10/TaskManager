package com.hitss.springboot.task_manager.service.user;

import java.util.List;
import java.util.Optional;
import com.hitss.springboot.task_manager.model.entity.UserEntity;

public interface UserService {
    List<UserEntity> findAll();
    UserEntity save(UserEntity user);

    boolean existsByUsername(String username);
}
