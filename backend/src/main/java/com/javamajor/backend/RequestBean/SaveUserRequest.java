package com.javamajor.backend.RequestBean;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javamajor.backend.Entity.Category;
import com.javamajor.backend.Entity.Question;
import com.javamajor.backend.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@JsonSerialize
@JsonDeserialize
@Getter
@Setter

public class SaveUserRequest {

    @JsonProperty( "username")
    @NotNull
    private String username;

    @JsonProperty( "sex")
    @NotNull
    private String sex;

    @JsonProperty( "age")
    @NotNull
    private Integer age;

    @JsonProperty( "dob")
    @NotNull
    private Date date;

    @JsonProperty( "profession")
    @NotNull
    private String profession;

    @JsonProperty( "martial_status")
    private String martial_status;

    @JsonProperty( "address")
    @NotNull
    private String address;

    @JsonProperty( "email")
    @NotNull
    @Email
    private String email;

    @JsonProperty( "contact")
    @NotNull
    private String contact;

    @JsonProperty( "category")
    @NotNull
    private Category category;

    public User UserRequestToUser(){
        return User.builder()
                .username(this.username)
                .sex(this.sex)
                .age(this.age)
                .date(this.date)
                .profession(this.profession)
                .martial_status(this.martial_status)
                .address(this.address)
                .email(this.email)
                .contact(this.contact)
                .category(this.category)
                .build();
    }


}
