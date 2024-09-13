package com.javamajor.backend.RequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javamajor.backend.Entity.DepressionCategory;
import com.javamajor.backend.Entity.Result;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@JsonDeserialize
@Getter
@Setter
public class SaveResultRequest {

    @JsonProperty("session_id")
    @NotNull
    private Integer sessionID;

    @JsonProperty("user_id")
    @NotNull
    private Integer userID;

    public Result saveResultRequestToResult(DepressionCategory dc,Integer sumIndexPoints){
        return Result.builder()
                .sessionID(this.sessionID)
                .userID(this.userID)
                .depressionCategory(dc)
                .sumIndexPoints(sumIndexPoints)
                .build();
    }

    public Integer getSessionID(){
        return sessionID;
    }

    public Integer getUserID(){
        return userID;
    }
}