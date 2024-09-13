package com.javamajor.backend.Service;

import com.javamajor.backend.Entity.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ResponseService {
    String saveVideoFile(MultipartFile file,String filepath);

    String saveResponseText(String filePath);

    String saveAudioFile(MultipartFile file,String filepath);

    Response saveResponse(Response response);

    double[] videoEmotions(String filePath);

    boolean[] audioEmotions(String filePath);

    boolean[] textEmotions(String textResponse);

    double affirmationPercentage(String textResponse);

    Integer weightIndexCalculator(double affirmationPercentage);

    List<Response> getResponses(Integer SessionID);

}
