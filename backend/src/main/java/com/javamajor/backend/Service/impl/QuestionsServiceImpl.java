package com.javamajor.backend.Service.impl;

import com.javamajor.backend.Entity.Question;
import com.javamajor.backend.Entity.User;
import com.javamajor.backend.Repository.QuestionsRepository;
import com.javamajor.backend.Service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    QuestionsRepository questionsRepository;

    @Override
    public List<Question> saveQuestion(List<Question> questions){
        questionsRepository.saveAll(questions);
        return questionsRepository.findAll();
    }

    @Override
    public List<Question> getQuestion(User user){
        return questionsRepository.findAllByCategory(user.getCategory());
    }
}
