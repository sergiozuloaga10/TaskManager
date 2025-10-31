package com.hitss.springboot.task_manager.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hitss.springboot.task_manager.model.entity.RoleEntity;
import com.hitss.springboot.task_manager.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hitss.springboot.task_manager.repository.RoleRepository;
import com.hitss.springboot.task_manager.repository.UserRepository;
import com.hitss.springboot.task_manager.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public UserEntity save(UserEntity user) {
        // TODO Auto-generated method stub
        List<RoleEntity> roles = new ArrayList<>();
        Optional<RoleEntity> optionalRole = roleRepository.findByName("ROLE_USER");

        optionalRole.ifPresent(roles::add);

        if(user.isAdmin()){
            Optional<RoleEntity> optional = roleRepository.findByName("ROLE_ADMIN");
            optional.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        // TODO Auto-generated method stub
        return userRepository.existsByUsername(username);
    }



}