package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Integer> {
}
