package com.javamajor.backend.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "question", nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

//
//    // Private constructor to enforce the use of the builder
//    private Question(Builder builder) {
//        this.id = builder.id;
//        this.question = builder.question;
//        this.category = builder.category;
//    }
//
//    // Public static builder class
//    public static class Builder {
//        private  Integer id;
//        private String question;
//        private Integer category;
//
//
//        public Builder question(String question) {
//            this.question = question;
//            return this;
//        }
//
//        public Builder category(Integer category) {
//            this.category = category;
//            return this;
//        }
//
//        public Builder id(Integer id){
//            this.id = id;
//            return this;
//        }
//
//        public Question build() {
//            return new Question(this);
//        }
//    }

}