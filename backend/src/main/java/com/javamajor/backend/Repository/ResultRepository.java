package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result,Integer> {
    Result findBySessionID(Integer session_id);

    List<Result> findAllByUserID(Integer user_id);
}
