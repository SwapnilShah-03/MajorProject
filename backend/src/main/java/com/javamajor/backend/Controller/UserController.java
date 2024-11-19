package com.javamajor.backend.Controller;

import com.javamajor.backend.Entity.Category;
import com.javamajor.backend.Entity.Question;
import com.javamajor.backend.Entity.User;
import com.javamajor.backend.RequestBean.SaveQuestionRequest;
import com.javamajor.backend.RequestBean.SaveUserRequest;
import com.javamajor.backend.Service.QuestionsService;
import com.javamajor.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@Valid @RequestBody SaveUserRequest userRequest) {
        System.out.println("Inside User Controller");
        Category c = userService.sortCategory(userRequest.getAge());
        System.out.println(c);
        return ResponseEntity.ok(userService.saveUser(userRequest.UserRequestToUser(c)));
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        System.out.println("I am in get user controller for username :" + username);
        return ResponseEntity.ok(userService.getUser(username));
    }

}