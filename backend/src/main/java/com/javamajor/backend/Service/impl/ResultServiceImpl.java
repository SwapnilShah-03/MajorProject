package com.javamajor.backend.Service.impl;

import com.javamajor.backend.Entity.DepressionCategory;
import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Entity.Result;
import com.javamajor.backend.Repository.ResultRepository;
import com.javamajor.backend.Service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    ResultRepository resultRepository;

    @Override
    public Integer sumIndexPoints(List<Response> responses){
        int sumIndexPoints = 0;
        for(int i=0;i<8;++i){
            sumIndexPoints += responses.get(i).getweightIndex();
        }
        return sumIndexPoints;
    }

    @Override
    public DepressionCategory getDepressionCategory(Integer sumIndexPoints){
        if (sumIndexPoints > 20){return DepressionCategory.SEVERE_DEPRESSSION;}
        else if (sumIndexPoints>15){return DepressionCategory.MODERATELY_SEVERE_DEPRESSSION;}
        else if (sumIndexPoints>10){return DepressionCategory.MODERATE_DEPRESSSION;}
        else if (sumIndexPoints>5){return DepressionCategory.MILD_DEPRESSSION;}
        else{return DepressionCategory.MINIMAL_DEPRESSSION;}
    }

    @Override
    public Result saveResult(Result result){
        return resultRepository.save(result);
    }

    @Override
    public Result getResult(Integer sessionID){
        return resultRepository.findBySessionID(sessionID);
    }

    @Override
    public List<Result> getAllResults (Integer userID){
        return resultRepository.findAllByUserID(userID);
    }
}
