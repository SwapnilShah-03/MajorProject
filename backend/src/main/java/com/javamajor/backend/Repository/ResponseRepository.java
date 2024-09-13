package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Integer> {
    List<Response> findAllBySessionID(Integer SessionID);
}
