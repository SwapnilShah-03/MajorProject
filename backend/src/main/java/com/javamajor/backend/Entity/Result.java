package com.javamajor.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "result")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @NotNull
    @Column(name = "session_id")
    private Integer sessionID;

    @NotNull
    @Column(name =  "user_id")
    private Integer userID;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "depression_category")
    private DepressionCategory depressionCategory;


    @NotNull
    @Column (name = "sum_index_points")
    private Integer sumIndexPoints;



}
