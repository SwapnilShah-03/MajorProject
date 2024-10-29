import sys
import moviepy.editor as mp
import speech_recognition as sr

def mp4_to_wav(mp4_file, wav_file):
    # Load the MP4 file and extract the audio
    clip = mp.VideoFileClip(mp4_file)
    clip.audio.write_audiofile(wav_file)

def extract_text_from_wav(wav_file):
    recognizer = sr.Recognizer()
    audio_file = sr.AudioFile(wav_file)

    with audio_file as source:
        audio_data = recognizer.record(source)

    try:
        # Convert the speech to text using Google API
        text = recognizer.recognize_google(audio_data)
        return text
    except sr.UnknownValueError:
        return "Could not understand audio."
    except sr.RequestError as e:
        return f"Could not request results from Google Speech Recognition service; {e}"

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("No file path provided!")
        sys.exit(1)

    mp4_file_path = sys.argv[1]
    wav_file_path = "output.wav"

    # Convert MP4 to WAV
    mp4_to_wav(mp4_file_path, wav_file_path)

    # Extract text from the WAV file
    extracted_text = extract_text_from_wav(wav_file_path)

    # Print the extracted text to be captured by Java
    print("Extracted response text:"+extracted_text)
