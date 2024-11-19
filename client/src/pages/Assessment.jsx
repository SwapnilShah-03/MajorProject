/* eslint-disable react/prop-types */
/* eslint-disable react-hooks/exhaustive-deps */
import { useState, useEffect, useRef, useContext } from "react";
import axios from "axios";
import { UserContext } from "../userContext";
import { useNavigate } from "react-router-dom"; // Import the UserContext
const Assessment = ({ category }) => {
  const navigate = useNavigate();
  const [questions, setQuestions] = useState([]);
  const [sessionId, setSessionId] = useState(null);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [isLastQuestion, setIsLastQuestion] = useState(false);
  const [mediaRecorder, setMediaRecorder] = useState(null);
  const [recordedChunks, setRecordedChunks] = useState([]);
  const videoRef = useRef(null);
  const [mediaStream, setMediaStream] = useState(null);
  const [isRecording, setIsRecording] = useState(false);
  const [hasRecorded, setHasRecorded] = useState(false);

  const { user } = useContext(UserContext); // Access user and sessionId from context
  const saveResults = async () => {
    try {
      const response = await axios.post(
        "/result/save",
        {
          session_id: sessionId,
          user_id: user.id,
        },
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      console.log("Result saved successfully", response.data);
      navigate("/results");
      console.log("Navigate");
    } catch (error) {
      console.error("Error saving results:", error);
    }
  };

  const reEvaluate = async () => {
    const reEvaluateResponse = await axios.put(
      `/response/delete/${Number(sessionId)}`
    );
    console.log(reEvaluateResponse.data);
    setCurrentQuestionIndex(0);
    setIsLastQuestion(false);
  };
  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const response = await axios.get(
          `/questions/getQuestions/${user.username}`,
          {
            withCredentials: true,
          }
        );
        // console.log(user)
        setQuestions(response.data.questionsList); // Update to match data structure
        setSessionId(response.data.session.id);
      } catch (error) {
        console.error("Error fetching questions:", error);
      }
    };

    if (user) fetchQuestions();

    const startVideoStream = async () => {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: true,
          audio: true,
        });
        if (videoRef.current) {
          videoRef.current.srcObject = stream;
        }
        setMediaStream(stream);
      } catch (error) {
        console.error("Error accessing the camera", error);
      }
    };

    startVideoStream();

    return () => {
      if (mediaStream) {
        mediaStream.getTracks().forEach((track) => track.stop());
      }
    };
  }, [category, user]); // Removed mediaStream dependency to prevent cleanup each render

  const startRecording = () => {
    if (mediaStream) {
      const recorder = new MediaRecorder(mediaStream);
      setMediaRecorder(recorder);
      setRecordedChunks([]); // Clear previous recordings on new start
      recorder.ondataavailable = (event) => {
        if (event.data.size > 0) {
          setRecordedChunks((prev) => [...prev, event.data]);
        }
      };
      recorder.start();
      setIsRecording(true);
      setHasRecorded(false);
    }
  };

  const stopRecording = () => {
    if (mediaRecorder) {
      mediaRecorder.stop();
      setIsRecording(false);
      setHasRecorded(true);
    }
  };

  const reRecord = () => {
    setRecordedChunks([]);
    setHasRecorded(false);
    startRecording();
  };

  const saveAndUploadVideo = async () => {
    const questionID = questions[currentQuestionIndex].id;
    const sessionID = sessionId;
    console.log("Session ID " + sessionID);
    const userID = user.id;
    console.log("User ID " + userID);

    if (!sessionID || !questionID) {
      console.error("Session ID or Question ID is undefined.");
      return;
    }

    const blob = new Blob(recordedChunks, { type: "video/mp4" });
    const file = new File([blob], "response.mp4", { type: "video/mp4" });

    const formData = new FormData();
    formData.append("session_id", sessionID);
    formData.append("user_id", userID);
    formData.append("question_id", questionID);
    formData.append("file", file);

    try {
      const response = await axios.post("/response/save", formData, {
        headers: { "Content-Type": "multipart/form-data" },
        withCredentials: true,
      });
      console.log("Upload successful", response.data);

      if (currentQuestionIndex === questions.length - 1) {
        setIsLastQuestion(true);
      } else {
        setCurrentQuestionIndex((prev) => (prev + 1) % questions.length);
      }

      setRecordedChunks([]);
      setHasRecorded(false);
      setIsRecording(false);
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  return (
    <div className="h-screen">
      <div className="max-w-2xl mt-24 mx-auto p-5 bg-white shadow-lg rounded-lg">
        {isLastQuestion ? (
          <div className="text-center space-y-6">
            <h2 className="text-xl font-Poppins text-gray-700">
              You've completed all questions!
            </h2>
            <div className="flex justify-center space-x-4">
              <button
                onClick={saveResults}
                className="bg-green-600 text-white font-Poppins py-2 px-4 rounded hover:bg-blue-700"
              >
                Get Results
              </button>
              <button
                onClick={reEvaluate}
                className="bg-red-600 text-white font-Poppins py-2 px-4 rounded hover:bg-yellow-700"
              >
                Re-evaluate
              </button>
            </div>
          </div>
        ) : questions?.length > 0 ? (
          <div className="question-box space-y-6">
            <h2 className="text-lg text-center font-Poppins text-gray-700">
              {questions[currentQuestionIndex]?.question}
            </h2>

            <div className="video-feed">
              <video
                ref={videoRef}
                autoPlay
                playsInline
                muted
                className="w-full h-64 object-cover border border-gray-300 rounded-md"
              />
            </div>

            <div className="flex justify-between items-center">
              {!isRecording && !hasRecorded ? (
                <button
                  onClick={startRecording}
                  className="bg-blue-600 text-white font-Poppins py-2 px-4 rounded hover:bg-blue-700"
                >
                  Start Recording
                </button>
              ) : isRecording ? (
                <button
                  onClick={stopRecording}
                  className="bg-red-600 text-white font-Poppins py-2 px-4 rounded hover:bg-red-700"
                >
                  Stop Recording
                </button>
              ) : (
                <>
                  <button
                    onClick={reRecord}
                    className="bg-yellow-600 text-white font-Poppins py-2 px-4 rounded hover:bg-yellow-700"
                  >
                    Re-record
                  </button>
                  <button
                    onClick={saveAndUploadVideo}
                    className="bg-green-600 text-white font-Poppins py-2 px-4 rounded hover:bg-green-700"
                  >
                    Save & Upload Video
                  </button>
                </>
              )}
            </div>
          </div>
        ) : (
          <div className="text-center text-red-500">
            No questions available for the selected category.
          </div>
        )}
      </div>
    </div>
  );
};

export default Assessment;
