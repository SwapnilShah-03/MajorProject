import cv2
import sys
import numpy as np
import librosa
from moviepy.editor import VideoFileClip
import cv2
import numpy as np
import librosa
import librosa.display
import matplotlib.pyplot as plt
import IPython.display as ipd
from keras.models import load_model
import speech_recognition as sr
from pydub import AudioSegment


# Function to extract audio features
def extract_features(data, sample_rate):
    # ZCR
    zcr = np.mean(librosa.feature.zero_crossing_rate(y=data).T, axis=0)

    # Chroma_stft
    stft = np.abs(librosa.stft(data))
    chroma_stft = np.mean(librosa.feature.chroma_stft(S=stft, sr=sample_rate).T, axis=0)

    # MFCC
    mfcc = np.mean(librosa.feature.mfcc(y=data, sr=sample_rate).T, axis=0)

    # Root Mean Square Value
    rms = np.mean(librosa.feature.rms(y=data).T, axis=0)

    # MelSpectogram
    mel = np.mean(librosa.feature.melspectrogram(y=data, sr=sample_rate).T, axis=0)

    return np.hstack((zcr, chroma_stft, mfcc, rms, mel))

# Function to get audio features
def get_features(path):
    # Load audio file
    data, sample_rate = librosa.load(path, duration=2.5, offset=0.6)

    # Extract features
    features = extract_features(data, sample_rate)

    return features

# Example usage



if __name__ == "__main__":
  if len(sys.argv) < 2:
        print("No file path provided!")
        sys.exit(1)
  audio_features = get_features(sys.argv[1])
  emotion_labels = {0: 'angry', 1: 'disgust', 2: 'fear', 3: 'happy', 4: 'neutral', 5: 'sad', 6: 'surprise'}

  # Load the audio model and make predictions
  audio_model = load_model(r"C:/Users/Lightning/Desktop/college/Major_Project_Sem_6_and_7/website/PythonScripts/cnn.h5")
  audio_features = audio_features.reshape(1, -1)
  predictions_audio = audio_model.predict(audio_features)
  predicted_emotion_index = np.argmax(predictions_audio[0])  # Assuming batch_size=1

  # Map the predicted emotion index to its corresponding label
  predicted_emotion_label = emotion_labels[predicted_emotion_index]

  # Print the predicted emotion
  print("Predicted Audio Emotion:", predicted_emotion_label)
  probabilities_array = []

  # Iterate through each prediction
  for prediction in predictions_audio:
      probabilities_array.append(prediction.tolist())

  # Convert the list of lists to a numpy array
  probabilities_array = np.array(probabilities_array)

  # Print the probabilities array


