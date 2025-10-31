package com.hitss.springboot.task_manager.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.hitss.springboot.task_manager.service.user.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String>{

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userService == null) {
            return true;
        }
        return !userService.existsByUsername(username);
    }


}