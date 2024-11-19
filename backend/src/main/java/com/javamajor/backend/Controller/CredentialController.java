package com.javamajor.backend.Controller;

import com.javamajor.backend.Entity.User;
import com.javamajor.backend.RequestBean.CheckCredentialsRequest;
import com.javamajor.backend.RequestBean.SaveCredentialsRequest;
import com.javamajor.backend.Service.CredentialService;
import com.javamajor.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    CredentialService credentialService;

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Integer> saveCredentials(@Valid @RequestBody SaveCredentialsRequest saveCredentialsRequest){
        System.out.println("I am in save credential request");
        User user = userService.getUser(saveCredentialsRequest.getUsername());
        System.out.println(user);
        return ResponseEntity.ok(credentialService.savePassword(saveCredentialsRequest.CredentialRequestToCredential(user.getID())));
    }

    @PutMapping("/check")
    public ResponseEntity<?> checkCredentials(@Valid @RequestBody CheckCredentialsRequest checkCredentialsRequest){
        System.out.println("I am in save credential request");
        User user = userService.getUser(checkCredentialsRequest.getUsername());
        System.out.println(user.getID());
        if(credentialService.checkPassword(user.getID(), checkCredentialsRequest.getPassword())){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("Invalid Password");
    }

}
