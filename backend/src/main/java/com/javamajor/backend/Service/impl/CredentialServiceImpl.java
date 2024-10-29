package com.javamajor.backend.Service.impl;

import com.javamajor.backend.Entity.Credential;
import com.javamajor.backend.Repository.CredentialRepository;
import com.javamajor.backend.Service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public Boolean checkPassword(Integer userID, String Password){
       Credential c = credentialRepository.findByUserID(userID);
        if(c.getPassword().equals(Password)){
            return true;
        }
        return false;
    }

    @Override
    public Integer savePassword(Credential credential){
        Credential c = credentialRepository.save(credential);
        return c.getID();
    }
}
