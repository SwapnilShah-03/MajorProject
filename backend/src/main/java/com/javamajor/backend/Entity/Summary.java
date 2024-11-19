package com.javamajor.backend.Entity;

import lombok.Data;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;

import java.util.List;

@Data
public class Summary {
    private Integer sessionID;

    private String question;

    private String audioEmotion;

    private String textEmotion;

    private String facialEmotions;

    private Integer sentimentScore;

    public Summary(Integer sessionID, String question, String audioEmotion, String textEmotion, String facialEmotions, Integer sentimentScore){
        this.sessionID = sessionID;
        this.question = question;
        this.audioEmotion = audioEmotion;
        this.textEmotion = textEmotion;
        this.facialEmotions = facialEmotions;
        this.sentimentScore = sentimentScore;
    }


}
