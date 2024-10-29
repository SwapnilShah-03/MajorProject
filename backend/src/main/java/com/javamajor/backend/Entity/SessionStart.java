package com.javamajor.backend.Entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class SessionStart {
    private List<Question> questionsList;

    private Session session;

    public SessionStart(List<Question> questionsList,Session session){
        this.session =session;
        this.questionsList = questionsList;
    }

}
