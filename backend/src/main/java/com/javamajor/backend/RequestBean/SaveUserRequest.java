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

    @JsonProperty( "marital_status")
    private String marital_status;

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


    public User UserRequestToUser(Category c){
        return User.builder()
                .username(this.username)
                .sex(this.sex)
                .age(this.age)
                .date(this.date)
                .profession(this.profession)
                .marital_status(this.marital_status)
                .address(this.address)
                .email(this.email)
                .contact(this.contact)
                .category(c)
                .build();
    }

    public Integer getAge(){return this.age;}


}
