package com.hitss.springboot.task_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.springboot.task_manager.model.entity.UserEntity;
import com.hitss.springboot.task_manager.service.user.UserService;
import com.hitss.springboot.task_manager.utils.UtilErrors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserEntity> list(){
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity user, BindingResult result){
        if (result.hasFieldErrors()) {
            return UtilErrors.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity user, BindingResult result){
        user.setAdmin(false);
        return create(user, result);
    }
}