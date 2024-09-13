package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.Question;
import com.javamajor.backend.Entity.User;
import lombok.Singular;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface QuestionsService {
    List<Question> saveQuestion(List<Question> questions);

    List<Question> getQuestion(User user);
}
