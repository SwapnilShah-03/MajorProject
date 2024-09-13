package com.javamajor.backend.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "user_id")
    @NotNull
    private Integer userID;

    @Column(name = "session_date")
    @NotNull
    private Date sessionDate;

    @Column(name = "total_time")
    @NotNull
    private Time totalTime;
}
