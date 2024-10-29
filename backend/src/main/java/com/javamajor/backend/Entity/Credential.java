package com.javamajor.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "login-credentials")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @NotNull
    @Column(name =  "user_id")
    private Integer userID;

    @NotNull
    @Column(name = "password")
    private String password;

    public Integer getID() {
        return id;
    }
    public String getPassword(){return password;}
}
