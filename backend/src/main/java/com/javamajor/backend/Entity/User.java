package com.javamajor.backend.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "sex")
    @NotNull
    private String sex;

    @Column(name = "age")
    @NotNull
    private Integer age;

    @Column(name = "dob")
    @NotNull
    private Date date;

    @Column(name = "profession")
    @NotNull
    private String profession;

    @Column(name = "martial_status")
    private String martial_status;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "email")
    @NotNull
    @Email
    private String email;

    @Column(name = "contact")
    @NotNull
    private String contact;

    @Column(name = "category")
    @NotNull
    private Category category;


    public @NotNull Category getCategory() {
        return category;
    }

    public Integer getID(){
        return id;
    }
}
