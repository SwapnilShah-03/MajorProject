package com.javamajor.backend.RequestBean;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javamajor.backend.Entity.Response;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Percentage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.IntegerSyntax;


public class SaveResponseRequest {

    private Integer sessionID;

    private Integer questionID;

    private MultipartFile file;

   public SaveResponseRequest(Integer sessionID,Integer questionID, MultipartFile file){
        this.sessionID =sessionID;
        this.questionID =
                questionID;
        this.file = file;
    }
    public  MultipartFile getFile() {
        return file;
    }

    public Integer getSessionID(){
        return sessionID;
    }

    public Integer getQuestionID(){
        return questionID;
    }

    public Response saveResponseRequestToResponse(String videoFilePath, String audioFilePath, String responseText,double[] videoEmotions,boolean[] audioEmotions,boolean[] textEmotions, double affirmationPerc, Integer weightIndex){
        return Response.builder()
                .sessionID(this.sessionID)
                .questionID(this.questionID)
                .recordingPath(videoFilePath)
                .audioFilePath(audioFilePath)
                .responseText(responseText)
                .videoEmotions(videoEmotions)
                .audioEmotions(audioEmotions)
                .affirmationPerc(affirmationPerc)
                .textEmotions(textEmotions)
                .weightIndex(weightIndex)
                .build();
    }

}
