import { useState, useEffect, useContext } from "react";
import axios from "axios";
import { UserContext } from "../userContext";
import { Link } from "react-router-dom";
import {
  List,
  ListItem,
  Card,
  CardHeader,
  Typography,
} from "@material-tailwind/react";

const Results = () => {
  const listItemStyle =
    "text-blue-gray-50 text-opacity-50 hover:bg-blue-gray-700 ease-in transition duration-150 hover:text-blue-gray-50 hover:text-opacity-100 font-Outfit font-normal text-lg";
  const [data, setData] = useState([]);
  //   const [sessionId, setSessionId] = useState(null);

  const { user } = useContext(UserContext);

  useEffect(() => {
    const fetchData = async () => {
      if (user) {
        try {
          console.log("User ID: ", user.id);
          console.log(typeof Number(user.id));

          const response = await axios.get(
            `/result/getResults/${Number(user.id)}`,
            {
              withCredentials: true,
            }
          );
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
        Welcome {user.username}! Here are your results:
      </Typography>
      <div className="w-full flex justify-center py-8">
        <Card
          variant="gradient"
          className="bg-blue-gray-900 h-auto w-full rounded-xl mx-10 p-2"
        >
          <CardHeader className="grid grid-cols-9 items-center bg-transparent shadow-none mx-5 mt-1">
            <Typography className="col-span-2 text-blue-gray-50 font-Outfit font-normal text-xl">
              User ID
            </Typography>
            <Typography className="col-span-2 text-blue-gray-50 font-Outfit font-normal text-xl">
              Session ID
            </Typography>
            <Typography className="col-span-3 text-blue-gray-50 font-Outfit font-normal text-xl text-center">
              Depression Category
            </Typography>
            <Typography className="col-span-2 text-blue-gray-50 font-Outfit font-normal text-xl text-center">
              Sum Index Points
            </Typography>
          </CardHeader>
          <hr className="mx-5 mt-2" />
          {data.length > 0 ? (
            <List>
              {data.map((d) => (
                <div className="grid gap-2" key={d.id}>
                  <Link to={`/summary/${d.sessionID}`}>
                    <ListItem
                      className={`grid grid-cols-9 items-center ${listItemStyle}`}
                    >
                      <div className="col-span-2">{d.userID}</div>
                      <div className="col-span-2">{d.sessionID}</div>
                      <div className="col-span-3 text-center">
                        {d.depressionCategory}
                      </div>
                      <div className="col-span-2 text-center">
                        {d.sumIndexPoints}
                      </div>
                    </ListItem>
                  </Link>
                </div>
              ))}
            </List>
          ) : (
            <p>No results available.</p>
          )}
        </Card>
      </div>
    </div>
  );
};

export default Results;
