package com.javamajor.backend.RequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javamajor.backend.Entity.Category;
import com.javamajor.backend.Entity.Question;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Bean;


@JsonSerialize
@JsonDeserialize
@Getter
@Setter

public class SaveQuestionRequest {

    @JsonProperty("question")
    @NotNull
    private String question;

    @JsonProperty("category")
    @NotNull
    private Category category;

    public Question toQuestion() {
        return  Question.builder()
                .question(this.question)
                .category(this.category)
                .build();
    }
}


