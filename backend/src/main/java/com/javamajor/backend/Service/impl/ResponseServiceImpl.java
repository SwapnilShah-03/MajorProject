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

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    ResponseRepository responseRepository;

    @Override
    public String saveVideoFile( MultipartFile file, String filepath) {
       mkDir(filepath);
       String videoFilePath = filepath + file.getOriginalFilename();
       return uploadFile(file,videoFilePath);
    }

    @Override
    public String saveAudioFile(MultipartFile file,String filepath){
        mkDir(filepath);
        String inputFilePath = filepath + file.getOriginalFilename();
        String outputFilePath = filepath + "audio.mp3";

        try {
            // Command to convert MP4 to MP3 using FFmpeg
            String[] command = {
                    "ffmpeg",
                    "-i", inputFilePath,
                    "-q:a", "0",
                    "-map", "a",
                    outputFilePath
            };

            // Create a ProcessBuilder to run the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);  // Redirect error stream to output

            // Start the process
            Process process = processBuilder.start();

            // Capture the output of the FFmpeg command
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);  // Print the output to the console
                }
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Conversion failed with exit code " + exitCode);
                return "";
            } else {
                System.out.println("Conversion successful! MP3 file created at: " + outputFilePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return outputFilePath;
    }

    @Override
    public String saveResponseText(String filePath){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major Project Sem 6 and 7/website/PythonScripts/convert_and_transcribe.py";
        // Return the output of the Python script
        return runPythonScript(filePath,scriptPath);

    }

    @Override
    public Response saveResponse(Response response){
        return responseRepository.save(response);
    }

    @Override
    public double[] videoEmotions(String filePath){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major Project Sem 6 and 7/website/PythonScripts/facial_emotion_detection.py";
        String input = runPythonScript(filePath,scriptPath);
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
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major Project Sem 6 and 7/website/PythonScripts/audio_emotion_detection.py";
        String predictedEmotion = runPythonScript(filePath,scriptPath);

        // Convert predicted emotion to a boolean array
        boolean[] emotionFlags = convertToBooleanArray(predictedEmotion);

        // Print the boolean array
        System.out.println(Arrays.toString(emotionFlags));
        return emotionFlags;
    }

    @Override
    public boolean[] textEmotions(String textResponse){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major Project Sem 6 and 7/website/PythonScripts/text_emotion_detection.py";
        String predictedEmotion = runPythonScript(textResponse,scriptPath);

        // Convert predicted emotion to a boolean array
        boolean[] emotionFlags = convertToBooleanArray(predictedEmotion);

        // Print the boolean array
        System.out.println(Arrays.toString(emotionFlags));
        return emotionFlags;
    }

    @Override
    public double affirmationPercentage(String textResponse){
        String scriptPath = "C:/Users/Lightning/Desktop/college/Major Project Sem 6 and 7/website/PythonScripts/affirmation_detection.py";
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
        StringBuilder output = new StringBuilder();
        try {
            String[] cmd = new String[]{"python3", scriptPath, filePath};

            // ProcessBuilder to execute the command
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);  // Redirect error stream to the output

            // Start the process
            Process process = pb.start();

            // Capture the output of the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                output.append("Error: Python script exited with code ").append(exitCode);
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
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


    @Override
    public List<Response> getResponses(Integer SessionID){
        return responseRepository.findAllBySessionID(SessionID);
    }
}
