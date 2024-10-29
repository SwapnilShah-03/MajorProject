package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Credential;
import com.javamajor.backend.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential,Integer> {
    Credential findByUserID(Integer id);
}
