package com.javamajor.backend.Controller;


import com.javamajor.backend.Entity.Response;
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

@RestController
@RequestMapping("/response")
public class ResponseController {

    @Autowired
    ResponseService responseService;

    @PostMapping("/save")
    public ResponseEntity<?> saveResponse(@Valid @RequestBody SaveResponseRequest saveResponseRequest){
        MultipartFile file = saveResponseRequest.getFile();
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a file!");
        }
        String uploadDir = "D:/STUDY/Project_uploads/"
                + saveResponseRequest.getSessionID()
                + "/"
                + saveResponseRequest.getQuestionID()
                + "/";
        String videoFilePath = responseService.saveVideoFile(file,uploadDir);
        String audioFilePath  =responseService.saveAudioFile(file,uploadDir);
        String responseText = responseService.saveResponseText(videoFilePath);
        if (videoFilePath.equals("") || audioFilePath.equals("") || responseText.equals("")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed or conversion failed");
        }
        double[] videoEmotions = responseService.videoEmotions(videoFilePath);
        boolean[] audioEmotions = responseService.audioEmotions(audioFilePath);
        boolean[] textEmotions = responseService.textEmotions(responseText);
        double affirmationPercentage = responseService.affirmationPercentage(responseText);
        Integer weightIndex = responseService.weightIndexCalculator(affirmationPercentage);
        Response r = responseService.saveResponse(saveResponseRequest.saveResponseRequestToResponse(videoFilePath,audioFilePath,responseText,videoEmotions,audioEmotions,textEmotions,affirmationPercentage, weightIndex));
        return ResponseEntity.ok(r);

    }

    @GetMapping("/get/{session_id}")
    public ResponseEntity<List<Response>> getAllResponses(@PathVariable Integer sessionID){
        return ResponseEntity.ok(responseService.getResponses(sessionID));
    }

}
