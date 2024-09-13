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




if __name__ == "__main__":
  if len(sys.argv) < 2:
        print("No file path provided!")
        sys.exit(1)

  mp4_file_path = sys.argv[1]
  video = VideoFileClip(mp4_file_path)

  # Extract the audio part
  audio = video.audio

  audio.write_audiofile("audio_text.wav", codec='pcm_s16le', bitrate='44100')
  # Save the audio as an MP3 file
  audio.write_audiofile("audio_with_faces.mp3")


  # Create an array to store frames with detected faces
  frames_with_faces = []

  # Load the Haar cascade classifier for face detection
  face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

  # Process each frame
  for frame in video.iter_frames(fps=30, dtype='uint8'):
      # Convert the frame to grayscale for face detection
      gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

      # Perform face detection
      faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30))
      # If faces are detected, save the frame along with detected faces
      if len(faces) > 0:
          frames_with_faces.append(frame)

      # Display the frame with detected faces
      for (x, y, w, h) in faces:
          cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

      # Display the frame
      cv2.imshow('Frame', frame)

      # Press 'q' to quit
      if cv2.waitKey(1) & 0xFF == ord('q'):
          break

  # Release video capture object
  video.close()
  cv2.destroyAllWindows()

  facial_model = load_model(r"face.h5")
  predicted_emotions = []

  for face_frame in frames_with_faces:
    face_frame_resized = cv2.resize(face_frame, (48, 48))
    
    # Convert the image to grayscale if necessary
    face_frame_gray = cv2.cvtColor(face_frame_resized, cv2.COLOR_BGR2GRAY)
    
    # Expand dimensions to add batch dimension and channel dimension
    face_frame_input = np.expand_dims(face_frame_gray, axis=0)
    face_frame_input = np.expand_dims(face_frame_input, axis=-1)  # Add channel dimension
    
    # Make predictions using the face model
    predictions = facial_model.predict(face_frame_input)
    
    # Get the index of the emotion with the highest probability
    predicted_emotion_index = np.argmax(predictions[0])  # Assuming batch_size=1
    
    # Store the predicted emotion index
    predicted_emotions.append(predicted_emotion_index)

  from collections import Counter

  # Define the emotion labels
  emotion_labels = {0: 'angry', 1: 'disgust', 2: 'fear', 3: 'happy', 4: 'neutral', 5: 'sad', 6: 'surprise'}

  # Initialize a counter to count occurrences of each emotion
  emotion_counter = Counter()

  for predicted_emotion_index in predicted_emotions:
      # Map the predicted emotion index to its corresponding label
      predicted_emotion_label = emotion_labels[predicted_emotion_index]
      
      # Increment the count for the detected emotion
      emotion_counter[predicted_emotion_label] += 1

  # Print the count of each detected emotion
  
  for emotion, count in emotion_counter.items():
      print(f"{emotion}: {count}")