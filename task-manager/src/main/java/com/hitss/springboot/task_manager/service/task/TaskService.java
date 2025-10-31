package com.hitss.springboot.task_manager.service.task;

import com.hitss.springboot.task_manager.model.entity.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskEntity> findAll();
    Optional<TaskEntity> findById(Long id);
    Optional<TaskEntity> update(Long id, TaskEntity taskEntity);
    Optional<TaskEntity> delete(Long id);
    TaskEntity save(TaskEntity task);
}
