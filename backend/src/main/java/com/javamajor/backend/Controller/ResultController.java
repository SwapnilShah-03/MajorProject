package com.javamajor.backend.Controller;


import com.javamajor.backend.Entity.DepressionCategory;
import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Entity.Result;
import com.javamajor.backend.RequestBean.SaveResultRequest;
import com.javamajor.backend.Service.ResponseService;
import com.javamajor.backend.Service.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    ResponseService responseService;

    @Autowired
    ResultService resultService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveResult(@Valid @RequestBody SaveResultRequest saveResultRequest){
        System.out.println(saveResultRequest.getSessionID());
        System.out.println(saveResultRequest.getUserID());
        List<Response> responses = responseService.getResponses(saveResultRequest.getSessionID());
        Integer sumIndexPoints = resultService.sumIndexPoints(responses);
        DepressionCategory dc = resultService.getDepressionCategory(sumIndexPoints);
        return ResponseEntity.ok(resultService.saveResult(saveResultRequest.saveResultRequestToResult(dc,sumIndexPoints)));
    }

    @GetMapping("/get_session/{sessionID}")
    public ResponseEntity<Result> getResultBySessionID(@PathVariable Integer sessionID){
        return ResponseEntity.ok(resultService.getResult(sessionID));
    }

    @GetMapping("/getResults/{userID}")
    public ResponseEntity<List<Result>> getResultByUserID(@PathVariable Integer userID){
        System.out.println(userID);
        List<Result> r = resultService.getAllResults(userID) ;
        System.out.println(r);
        return ResponseEntity.ok(r);
    }


}
