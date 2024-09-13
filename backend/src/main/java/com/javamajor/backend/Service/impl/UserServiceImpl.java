package com.javamajor.backend.Service.impl;

import com.javamajor.backend.Entity.User;
import com.javamajor.backend.Repository.UserRepository;
import com.javamajor.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(User user){
        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String username){
        return userRepository.findByUsername(username);
    }
}
