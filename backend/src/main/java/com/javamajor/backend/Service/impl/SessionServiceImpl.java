package com.javamajor.backend.Service.impl;


import com.javamajor.backend.Entity.Session;
import com.javamajor.backend.Entity.User;
import com.javamajor.backend.Repository.SessionRepository;
import com.javamajor.backend.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public Session saveSession(User user){
        Session s = Session.builder()
                .userID(user.getID())
                .sessionDate(java.time.LocalDateTime.now())
                .totalTime(0)
                .build();
        return sessionRepository.save(s);
    }
}
