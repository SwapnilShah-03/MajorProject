package com.javamajor.backend.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Percentage;
import lombok.*;

@Entity
@Table(name = "response")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "session_id")
    @NotNull
    private Integer sessionID;

    @NotNull
    @Column(name = "question_id")
    private Integer questionID;

    @NotNull
    @Column(name = "recording_path")
    private String recordingPath;

    @NotNull
    @Column(name = "audio_file_path")
    private String audioFilePath;

    @NotNull
    @Column(name = "response_text")
    private String responseText;

    @NotNull
    @Column(name = "affirmation_perc")
    @Percentage
    private double affirmationPerc;

    @NotNull
    @Column(name ="video_emotions")
    private double[] videoEmotions;

    @NotNull
    @Column(name="audio_emotion")
    private String audioEmotion;

    @NotNull
    @Column(name = "text_emotion")
    private String textEmotion;

    @NotNull
    @Column(name = "weight_index")
    private Integer weightIndex;

    public Integer getweightIndex(){
        return weightIndex;
    }

    public @NotNull Integer getQuestionID() {
        return questionID;
    }

    public @NotNull String getAudioEmotion() {
        return audioEmotion;
    }

    public @NotNull String getTextEmotion() {
        return textEmotion;
    }

    @NotNull
    public double[] getVideoEmotions() {
        return videoEmotions;
    }
}
