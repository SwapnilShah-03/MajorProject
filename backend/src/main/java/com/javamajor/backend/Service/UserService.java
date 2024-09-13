package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.User;

public interface UserService {
    User saveUser(User user);

    User getUser(String username);

}
