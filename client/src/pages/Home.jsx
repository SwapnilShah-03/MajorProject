import { Card, CardBody, Typography, Button } from "@material-tailwind/react";
import { Link } from "react-router-dom";

import Carousel from "../components/Carousel";
export default function Home() {
  return (
    <div className="bg-white h-full flex flex-col justify-center items-center px-4 sm:px-6 lg:px-8 text-gray-900">
      <Typography className="text-center text-6xl font-medium font-Outfit mt-8">
        Welcome to MindLens!
      </Typography>
      <Typography className="text-center text-2xl font-normal font-Outfit mt-3">
        Your journey to a brighter mind starts here.
      </Typography>
      <Link
        to="/assessment"
        className="my-8 px-6 py-5 rounded-full bg-blue-500 hover:bg-blue-600 text-white"
      >
        <Typography className="text-center text-2xl font-normal font-Outfit">
          Start Your Assessment
        </Typography>
      </Link>
      <Carousel />
      <div className="my-8 max-w-4xl">
        <Typography className="text-2xl font-Outfit mb-4 text-center">
          Our Story
        </Typography>
        <div className="flex justify-center">
          <Typography className="text-lg font-Outfit w-1/2">
            MindLens was born out of a passion for mental health and well-being.
            The founders, a group of psychologists and tech enthusiasts, noticed
            a gap in accessible mental health tools. They envisioned an app that
            could help people detect early signs of depression and provide them
            with the resources they need to seek help.
          </Typography>
          <Typography className="text-lg font-Outfit mt-4 w-1/2">
            With advanced AI algorithms and a user-friendly interface, MindLens
            offers personalized assessments and actionable insights. The app
            aims to break the stigma around mental health and empower users to
            take control of their mental well-being.
          </Typography>
          <Typography className="text-lg font-Outfit mt-4 w-1/2">
            MindLens is more than just an app; it's a community of support,
            understanding, and hope. Join us on this journey to a brighter mind
            and a happier life.
          </Typography>
        </div>
      </div>
    </div>
  );
}
