package com.hitss.springboot.task_manager.repository;

import com.hitss.springboot.task_manager.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
}
