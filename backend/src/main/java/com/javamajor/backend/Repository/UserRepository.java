package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
