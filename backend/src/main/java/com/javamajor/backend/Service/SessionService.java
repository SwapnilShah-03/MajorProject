package com.javamajor.backend.Service;


import com.javamajor.backend.Entity.Session;
import com.javamajor.backend.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {
    Session saveSession(User user);
}
