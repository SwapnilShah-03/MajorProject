package com.javamajor.backend.Service.impl;


import com.javamajor.backend.Entity.Response;
import com.javamajor.backend.Repository.ResponseRepository;
import com.javamajor.backend.Service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    ResponseRepository responseRepository;

    @Override
    public String saveVideoFile( MultipartFile file, String filepath) {
        System.out.println("In save video file service");
       mkDir(filepath);
       String videoFilePath = filepath + file.getOriginalFilename();
       return uploadFile(file,videoFilePath);
    }


    @Override
    public String saveAudioFile(String videoFilePath){
        System.out.println("In save audio file service");
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/audio_convert.py";
        String output = runPythonScript(videoFilePath,scriptPath);
        String regex = "MP3 File Path:(.+)";
        String filePath =  extractor(output,regex);
        System.out.println(filePath);
        return filePath;
    }

    @Override
    public String saveResponseText(String filePath){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/convert_and_transcribe.py";
        String regex = "Extracted response text:(.+)";
        String output = runPythonScript(filePath,scriptPath);
        return extractor(output,regex);

    }

    @Override
    public Response saveResponse(Response response){
        return responseRepository.save(response);
    }

    @Override
    public double[] videoEmotions(String filePath){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/facial_emotion_detection.py";
        String output = runPythonScript(filePath,scriptPath);
        String regex = "Extracted Video Emotions:\\s*(.+)";
        String input = extractor(output,regex);
        System.out.println(input);
        String[] emotionsOrder = {"angry", "disgust", "fear", "happy", "neutral", "sad", "surprise"};

        // Parse the input string and get emotion counts
        Map<String, Integer> emotionCounts = parseEmotionString(input);

        // Calculate total frames
        int totalFrames = emotionCounts.values().stream().mapToInt(Integer::intValue).sum();

        // Create an array to store the percentage of each emotion in the correct order
        double[] emotionPercentages = new double[emotionsOrder.length];

        // Calculate the percentage for each emotion and place it in the correct position
        for (int i = 0; i < emotionsOrder.length; i++) {
            String emotion = emotionsOrder[i];
            int count = emotionCounts.getOrDefault(emotion, 0);  // Default to 0 if the emotion is missing
            if (totalFrames > 0) {
                emotionPercentages[i] = (double) count / totalFrames * 100;
            } else {
                emotionPercentages[i] = 0; // Handle case where totalFrames is 0 to avoid division by zero
            }
        }

        // Print out the percentages for each emotion
        for (int i = 0; i < emotionsOrder.length; i++) {
            System.out.println(emotionsOrder[i] + ": " + emotionPercentages[i] + "%");
        }
        return emotionPercentages;
    }


    @Override
    public boolean[] audioEmotions(String filePath){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/audio_emotion_detection.py";
        String output = runPythonScript(filePath,scriptPath);
        String regex = "Predicted Audio Emotion:(.+)";
        String predictedEmotion = extractor(output,regex);
        // Convert predicted emotion to a boolean array
        boolean[] emotionFlags = convertToBooleanArray(predictedEmotion);

        // Print the boolean array
        System.out.println(Arrays.toString(emotionFlags));
        return emotionFlags;
    }

    @Override
    public boolean[] textEmotions(String textResponse){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/text_emotion_detection.py";
        String output= runPythonScript(textResponse,scriptPath);
        String regex = "Predicted Text Emotion:(.+)";
        String predictedEmotion = extractor(output,regex);
        // Convert predicted emotion to a boolean array
        String[] emotionsOrder = {"sadness", "joy", "love", "anger", "fear", "surprise"};
        boolean[] emotionFlags = convertToBooleanArray(predictedEmotion,emotionsOrder);

        // Print the boolean array
        System.out.println(Arrays.toString(emotionFlags));
        return emotionFlags;
    }

    @Override
    public double affirmationPercentage(String textResponse){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/affirmation_detection.py";
        String affirmationPercentage = runPythonScript(textResponse,scriptPath);
        return Double.parseDouble(affirmationPercentage);
    }

    @Override
    public Integer weightIndexCalculator(double affirmationPercentage){
        if (affirmationPercentage > 85){ return 3;}
        else if(affirmationPercentage > 70){return 2;}
        else if (affirmationPercentage> 40){return 1;}
        else {return 0;}
    }


    public static boolean[] convertToBooleanArray(String predictedEmotion) {
        String[] emotionsOrder = {"angry", "disgust", "fear", "happy", "neutral", "sad", "surprise"};
        boolean[] emotionFlags = new boolean[emotionsOrder.length];

        // Find the index of the predicted emotion and set the corresponding flag to true
        for (int i = 0; i < emotionsOrder.length; i++) {
            if (emotionsOrder[i].equalsIgnoreCase(predictedEmotion)) {
                emotionFlags[i] = true;
            } else {
                emotionFlags[i] = false;
            }
        }

        return emotionFlags;
    }


    public static boolean[] convertToBooleanArray(String predictedEmotion,String[] emotionsOrder) {

        boolean[] emotionFlags = new boolean[emotionsOrder.length];

        // Find the index of the predicted emotion and set the corresponding flag to true
        for (int i = 0; i < emotionsOrder.length; i++) {
            if (emotionsOrder[i].equalsIgnoreCase(predictedEmotion)) {
                emotionFlags[i] = true;
            } else {
                emotionFlags[i] = false;
            }
        }

        return emotionFlags;
    }

    public String uploadFile(MultipartFile file,String filePath){
        try {
            File destFile = new File(filePath);
            file.transferTo(destFile);
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void mkDir(String dirPath){
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static String runPythonScript(String filePath, String scriptPath){
        System.out.println("In python run script function");
        System.out.println(scriptPath);
        System.out.println(filePath);
        StringBuilder output = new StringBuilder();

        try {
            String[] cmd = new String[]{"python", scriptPath, filePath};
            System.out.println(cmd);

            // ProcessBuilder to execute the command
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);  // Redirect error stream to the output
            System.out.println("Process Build");
            // Start the process
            Process process = pb.start();
            System.out.println("Process Start");

            // Capture the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Output Captured");

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println(exitCode);
            System.out.println(output);
            if (exitCode != 0) {
                output.append("Error: Python script exited with code ").append(exitCode);
                System.out.println(output);
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return "";
        }
        return output.toString();

    }

    public static Map<String, Integer> parseEmotionString(String input) {
        Map<String, Integer> emotionCounts = new HashMap<>();

        // Split the input by newline to get each emotion line
        String[] lines = input.split("\n");

        // Loop through each line and extract the emotion and count
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String emotion = parts[0].trim();
                int count = Integer.parseInt(parts[1].trim());
                emotionCounts.put(emotion, count);
            }
        }

        return emotionCounts;
    }

    public static String extractor(String input, String regex) {
        // Define the regex pattern to match the desired string
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches and return the result
        if (matcher.find()) {
            return matcher.group(1).trim();  // Get the part after "MP3 File Path:" and trim any leading spaces
        }
        return null;  // Return null if no match is found
    }

    @Override
    public List<Response> getResponses(Integer SessionID){
        return responseRepository.findAllBySessionID(SessionID);
    }
}
