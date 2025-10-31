package com.hitss.springboot.task_manager.repository;

import com.hitss.springboot.task_manager.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
