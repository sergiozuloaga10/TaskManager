package com.hitss.springboot.task_manager.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class UtilErrors {

    public static ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach((e)->{
           errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
