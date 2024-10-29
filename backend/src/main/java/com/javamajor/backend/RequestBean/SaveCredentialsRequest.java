package com.javamajor.backend.RequestBean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.javamajor.backend.Entity.Credential;
import com.javamajor.backend.Entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@JsonDeserialize
@Getter
@Setter

public class SaveCredentialsRequest {

    @JsonProperty( "username")
    @NotNull
    private String username;

    @JsonProperty( "password")
    @NotNull
    private String password;


    public Credential CredentialRequestToCredential(Integer userID){
        return Credential.builder()
                .userID(userID)
                .password(this.password)
                .build();
    }

    public String getUsername(){return this.username;}
}
