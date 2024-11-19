package com.javamajor.backend.Repository;

import com.javamajor.backend.Entity.Response;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Integer> {
    List<Response> findAllBySessionID(Integer SessionID);

    @Modifying
    @Transactional
    @Query("DELETE FROM Response r WHERE r.sessionID = :sessionID")
    void deleteBySessionID(@Param("sessionID") Integer sessionID);
}
