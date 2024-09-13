package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.DepressionCategory;
import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultService {
    Integer sumIndexPoints(List<Response> responses);

    DepressionCategory getDepressionCategory(Integer sumIndexPoints);

    Result saveResult(Result result);

    Result getResult(Integer sessionID);

    List<Result> getAllResults (Integer userID);
}
