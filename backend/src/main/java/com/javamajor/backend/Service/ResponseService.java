package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Entity.Summary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ResponseService {
    String saveVideoFile(MultipartFile file,String filepath);

    String saveResponseText(String filePath);

    String saveAudioFile(String videoFilePath);

    Response saveResponse(Response response);

    double[] videoEmotions(String filePath);

    String audioEmotions(String filePath);

    String textEmotions(String textResponse);

    double affirmationPercentage(String textResponse);

    Integer weightIndexCalculator(double affirmationPercentage);

    List<Response> getResponses(Integer SessionID);

    List<Summary> getSummary(Integer sessionID);

    void deleteRepsonses(Integer sessionID);

}
