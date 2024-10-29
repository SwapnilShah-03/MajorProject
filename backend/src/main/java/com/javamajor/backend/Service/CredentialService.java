package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.Credential;
import org.springframework.stereotype.Service;

@Service
public interface CredentialService {

    Boolean checkPassword(Integer userID,String Password);

    Integer savePassword(Credential credential);


}
