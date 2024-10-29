package com.javamajor.backend.Service.impl;


import com.javamajor.backend.Entity.Session;
import com.javamajor.backend.Entity.User;
import com.javamajor.backend.Repository.SessionRepository;
import com.javamajor.backend.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    public Session saveSession(User user) {
        Session s = Session.builder()
                .userID(user.getID())
                .sessionDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))  // Convert LocalDateTime to Date
                .totalTime(new Time(0))
                .build();
        return sessionRepository.save(s);
    }
}
