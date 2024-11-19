package com.javamajor.backend.Controller;


import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Entity.Summary;
import com.javamajor.backend.RequestBean.SaveResponseRequest;
import com.javamajor.backend.Service.ResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    ResponseService responseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveResponse(
                                            @RequestParam("file") MultipartFile file,  @RequestParam("session_id") Integer sessionID,
                                            @RequestParam("question_id") Integer questionID){

        System.out.println(sessionID);
        System.out.println(questionID);
        System.out.println(file);
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a file!");
        }
        String uploadDir = "D:/STUDY/Project_uploads/"
                + sessionID
                + "/"
                + questionID
                + "/";
        System.out.println("In Response Controller");
        try{String videoFilePath = responseService.saveVideoFile(file,uploadDir);
            String audioFilePath  =responseService.saveAudioFile(videoFilePath);
            videoFilePath = videoFilePath.replaceAll(Pattern.quote(".mp4") + "$", "_re.mp4");
            String responseText = responseService.saveResponseText(videoFilePath);
            System.out.println(responseText);

            if (videoFilePath.equals("") || audioFilePath.equals("") || responseText.equals("")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed or conversion failed");
            }
        System.out.println("All File extractions Successful");

        String audioEmotions = responseService.audioEmotions(audioFilePath);
        String textEmotions = responseService.textEmotions(responseText);
//            String audioEmotions = "neutral";
//            String textEmotions = "joy";
        double affirmationPercentage = responseService.affirmationPercentage(responseText);
        Integer weightIndex = responseService.weightIndexCalculator(affirmationPercentage);
            double[] videoEmotions = responseService.videoEmotions(videoFilePath);
        System.out.println("All Emotion extractions Successful");
        System.out.println(videoEmotions);
        System.out.println(audioEmotions);
        System.out.println(textEmotions);
        System.out.println(affirmationPercentage);
        System.out.println(weightIndex);
        SaveResponseRequest saveResponseRequest = new SaveResponseRequest(sessionID,questionID,file);
        Response r = responseService.saveResponse(saveResponseRequest.saveResponseRequestToResponse(videoFilePath,audioFilePath,responseText,videoEmotions,audioEmotions,textEmotions,affirmationPercentage, weightIndex));
        return ResponseEntity.ok(r);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/get/{sessionID}")
    public ResponseEntity<List<Summary>> getAllResponses(@PathVariable Integer sessionID){
        return ResponseEntity.ok(responseService.getSummary(sessionID));
    }

    @PutMapping("/delete/{sessionID}")
    public ResponseEntity<?> reEvaluate(@PathVariable Integer sessionID){
        responseService.deleteRepsonses(sessionID);
        return ResponseEntity.ok("Deletion Successful");
    }



}
