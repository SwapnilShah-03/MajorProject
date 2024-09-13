package com.javamajor.backend.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javamajor.backend.Entity.Question;
import com.javamajor.backend.Entity.Session;
import com.javamajor.backend.Entity.User;
import com.javamajor.backend.RequestBean.SaveQuestionRequest;
import com.javamajor.backend.Service.QuestionsService;
import com.javamajor.backend.Service.SessionService;
import com.javamajor.backend.Service.UserService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionsService questionsService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @PostMapping("/save")
    public ResponseEntity<List<Question>> saveQuestion(@Valid @RequestBody List<SaveQuestionRequest> request) {
        ArrayList<Question> q = new ArrayList<Question>();
        for (int i=0;i<request.size();++i){
            q.add(request.get(i).toQuestion());
        }
        return ResponseEntity.ok(questionsService.saveQuestion(q));
    }

    @GetMapping("/getQuestions/{username}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String username){
        User user = userService.getUser(username);
        Session s = sessionService.saveSession(user);
        System.out.println(s);
        System.out.println(user);
        return ResponseEntity.ok(questionsService.getQuestion(user));
    }
}

