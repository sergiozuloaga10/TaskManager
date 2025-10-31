package com.hitss.springboot.task_manager.controller;

import com.hitss.springboot.task_manager.model.entity.TaskEntity;
import com.hitss.springboot.task_manager.service.task.TaskService;
import com.hitss.springboot.task_manager.utils.UtilErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@Tag(
        name = "Tasks",
        description = "Endpoints for managing tasks with CRUD operations"
)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(
            summary = "Get all Tasks",
            description = "Retrieves all registred tasks"
    )
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public List<TaskEntity> list() {
        return taskService.findAll();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<TaskEntity> optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()) {
            return ResponseEntity.ok(optionalTask.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TaskEntity task, BindingResult result) {
        if (result.hasFieldErrors()) {
            return UtilErrors.validation(result);
        }
        task.setId(null);
        if(task.getCompleted() == null){
            task.setCompleted(false);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(task));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody TaskEntity task, BindingResult result) {
        if (result.hasFieldErrors()) {
            return UtilErrors.validation(result);
        }
        return taskService.update(id, task)
                .<ResponseEntity<?>>map(t -> ResponseEntity.status(HttpStatus.CREATED).body(t))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return taskService.delete(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
