package com.hitss.springboot.task_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.springboot.task_manager.model.entity.UserEntity;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user){
        return ResponseEntity.ok("Este endpoint lo toma Spring Security");
    }
}
