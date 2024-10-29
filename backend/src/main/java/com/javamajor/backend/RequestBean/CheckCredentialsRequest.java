package com.javamajor.backend.RequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@JsonSerialize
@JsonDeserialize
@Getter
@Setter

public class CheckCredentialsRequest {

    @JsonProperty( "username")
    @NotNull
    private String username;

    @JsonProperty( "password")
    @NotNull
    private String password;

    public String getUsername(){return this.username;}

    public String getPassword(){return this.password;}

}