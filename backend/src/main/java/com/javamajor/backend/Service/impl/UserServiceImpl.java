package com.javamajor.backend.Service.impl;

import com.javamajor.backend.Entity.Category;
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

    @Override
    public Category sortCategory(Integer age){
        if(age < 7){return Category.EARLY_CHILDHOOD;}
        else if (age < 12) {return Category.MIDDLE_CHILDHOOD;}
        else if (age < 19) {return Category.ADOLESCENCE_CHILDHOOD;}
        else if (age < 30) {return Category.YOUNG_ADULTHOOD;}
        else if (age < 50) {return Category.MIDDLE_ADULTHOOD;}
        else {return Category.OLDER_ADULTHOOD;}
    }
}
