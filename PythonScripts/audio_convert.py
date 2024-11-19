
import sys
import re
import subprocess

 
def reencode_video(input_file, output_file):
    command = [
        'D:/APPS/ffmpeg/ffmpeg.exe',  # Replace with the actual path to ffmpeg.exe
        '-i', input_file,
        '-c:v', 'copy',
        '-c:a', 'aac', 
        output_file
    ]
    subprocess.run(command, check=True)

def extract_audio(input_file, output_file):
    command = [
        'D:/APPS/ffmpeg/ffmpeg.exe', '-i', input_file, '-q:a', '0', '-map', 'a', output_file
    ]
    subprocess.run(command, check=True)


    
if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("No file path provided!")
        sys.exit(1) 
    file_path = sys.argv[1]
    re_file_path = re.sub(r"\.mp4$", "_re.mp4",file_path)
    output_file_path = re.sub(r"\.mp4$", ".mp3",file_path)
    reencode_video(file_path, re_file_path)
    extract_audio(re_file_path, output_file_path)
    print('MP3 File Path:' + output_file_path)
    