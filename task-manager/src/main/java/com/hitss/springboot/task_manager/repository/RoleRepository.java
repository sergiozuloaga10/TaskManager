package com.hitss.springboot.task_manager.repository;

import java.util.Optional;

import com.hitss.springboot.task_manager.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByName(String name);
}