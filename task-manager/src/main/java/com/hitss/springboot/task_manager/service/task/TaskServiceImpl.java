package com.hitss.springboot.task_manager.service.task;

import com.hitss.springboot.task_manager.model.entity.TaskEntity;
import com.hitss.springboot.task_manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TaskEntity> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<TaskEntity> update(Long id, TaskEntity taskEntity) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            TaskEntity task = optionalTask.orElseThrow();
            task.setName(taskEntity.getName());
            task.setCompleted(taskEntity.getCompleted());

            return Optional.of(taskRepository.save(task));
        }

        return optionalTask;
    }

    @Override
    public Optional<TaskEntity> delete(Long id) {
        Optional<TaskEntity> optionalTask = taskRepository.findById(id);
        optionalTask.ifPresent(p -> taskRepository.delete(p));

        return optionalTask;
    }

    @Transactional
    @Override
    public TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }
}
