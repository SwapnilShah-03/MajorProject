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
  from transformers import pipeline

  # Load the classifier pipeline
  classifier = pipeline("text-classification", model='bhadresh-savani/bert-base-uncased-emotion', return_all_scores=True)
      
  # Perform classification
  prediction = classifier(sys.argv[1])

  # Extract the label with the maximum probability
  max_score_label = max(prediction[0], key=lambda x: x['score'])['label']

  # Print the label
  print("Predicted emotion:", max_score_label)
