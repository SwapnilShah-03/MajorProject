/* eslint-disable react/jsx-key */
import { useState, useEffect, useContext } from "react";
import { UserContext } from "../userContext";
import axios from "axios";
import { useLoaderData, useParams } from "react-router-dom";
import {
  List,
  ListItem,
  Card,
  CardHeader,
  Typography,
} from "@material-tailwind/react";

const Summary = () => {
  const { sid } = useParams();
  const sessionData = useLoaderData();
  const [data, setData] = useState([]);
  const { user } = useContext(UserContext);
  const listItemStyle =
    "text-blue-gray-50 text-opacity-50 hover:bg-blue-gray-700 ease-in transition duration-150 hover:text-blue-gray-50 hover:text-opacity-100 font-Outfit font-normal text-lg";
  useEffect(() => {
    const fetchData = async () => {
      if (user) {
        try {
          const response = await axios.get(`/response/get/${Number(sid)}`, {
            withCredentials: true,
          });
          //   console.log("These are the results: " + response.data);
          setData(response.data);
          //   const qResponse = await axios.get(
          //     `/questions/getQuestions/${user.username}`,
          //     {
          //       withCredentials: true,
          //     }
          //   );
          //   setSessionId(qResponse.data.session.id);
        } catch (error) {
          console.error(
            "Error fetching data: ",
            error.response || error.message
          );
        }
      } else {
        console.error("User not logged in");
      }
    };

    fetchData();
  }, [user]);

  return (
    <div className="min-h-screen">
      <Typography className="text-center text-2xl font-medium font-Outfit mt-8">
        Summary for Session ID: {sid}
      </Typography>
      <div className="w-full flex justify-center py-10">
        <Card
          variant="gradient"
          className="bg-blue-gray-900 h-auto w-full rounded-xl mx-10 p-2"
        >
          <CardHeader
            floated="false"
            className="grid grid-cols-8 text-center bg-transparent shadow-none mt-1"
          >
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-1">
              Sr. No.
            </Typography>
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-2">
              Question
            </Typography>
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-1">
              Audio Emotions
            </Typography>
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-2">
              Facial Emotions
            </Typography>
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-1">
              Textual Emotions
            </Typography>
            <Typography className="text-blue-gray-50 font-Outfit font-normal text-xl col-span-1">
              Sentiment Score
            </Typography>
          </CardHeader>
          <hr className="mx-5 mt-2" />
          <List>
            {data.map((d, index) => (
              <ListItem
                key={index}
                className={`grid grid-cols-8 text-center ${listItemStyle}`}
              >
                <div className="col-span-1">{index + 1}</div>
                <div className="col-span-2">{d.question}</div>
                <div className="col-span-1">{d.audioEmotion}</div>
                <div className="col-span-2">{d.facialEmotions}</div>
                <div className="col-span-1">{d.textEmotion}</div>
                <div className="col-span-1">{d.sentimentScore}</div>
              </ListItem>
            ))}
          </List>
        </Card>
      </div>
    </div>
  );
};

export default Summary;
