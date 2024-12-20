/* eslint-disable react/no-unescaped-entities */
import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useGoogleLogin } from "@react-oauth/google";
import { toast } from "react-hot-toast";
import { Button } from "@material-tailwind/react";
import { UserContext } from "../userContext";

const fixedInputClass =
  "rounded-md appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-blue-gray-900 sm:text-md";

export default function Login() {
  const navigate = useNavigate();
  const { setUser, setSessionId } = useContext(UserContext);
  const [googleLogin, setGoogleLogin] = useState(false);

  const [data, setData] = useState({
    username: "",
    password: "",
  });

  // const login = useGoogleLogin({
  //   onSuccess: async (tokenResponse, event) => {
  //     if (event) {
  //       event.preventDefault();
  //     }
  //     const userInfoResponse = await fetch(
  //       import.meta.env.VITE_GOOGLE_AUTH_URL,
  //       {
  //         method: "GET",
  //         headers: { Authorization: `Bearer ${tokenResponse.access_token}` },
  //       }
  //     );
  //     const userInfo = await userInfoResponse.json();
  //     document.cookie = `userinfo=${JSON.stringify(
  //       userInfo
  //     )}; expires=${new Date(Date.now() + 1000 * 60 * 60 * 24)}; path=/`;

  //     const email = userInfo.email;
  //     const verification = await axios.post("/authLogin", { e: email });
  //     console.log(verification);
  //     if (verification.data === "False") {
  //       toast.error("Email is not registered yet");
  //       navigate("/register");
  //     } else {
  //       toast.success("Logged in successfully!");
  //       const user = verification.data.user;
  //       setUser(user);

  //       // Fetch session ID after login
  //       try {
  //         const sessionResponse = await axios.get(`/questions/getQuestions/${user.username}`, { withCredentials: true });
  //         // setSessionId(sessionResponse.data.session.id);
  //       } catch (error) {
  //         console.error("Error fetching session ID:", error);
  //         toast.error("No session currently running.");
  //       }

  //       navigate("/");
  //     }
  //   },
  // });

  const handleValidation = () => {
    const { username, password } = data;
    if (!googleLogin && (username === "" || password === "")) {
      toast.error("All fields are required");
      return false;
    } else {
      return true;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (handleValidation()) {
      const { username, password } = data;
      try {
        const response = await axios.put("/credentials/check", {
          username,
          password,
        });
        if (response.data) {
          setData({
            username: "",
            password: "",
          });
          toast.success("Logged in successfully!");
          const user = response.data;
          setUser(user);
          console.log(user);

          navigate("/");
        } else {
          toast.error(response.data.message);
        }
      } catch (error) {
        console.log(error);
        toast.error("An error occurred during login.");
      }
    }
  };

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  return (
    <div className="h-[76.5vh] flex justify-center px-4 sm:px-6 lg:px-8 font-Outfit">
      <div className="max-w-md w-full my-12 items-center">
        <div className="mb-10">
          <div className="flex justify-center">
            <img
              alt="logo"
              className="h-[4rem] w-[4rem]"
              src="../../src/assets/images/logo.png"
            />
          </div>
          <h2 className="mt-5 text-center text-3xl font-extrabold text-blue-gray-900">
            Login to your account
          </h2>
          <p className="mt-5 text-center text-md text-gray-600 ">
            Don't have an account yet?{" "}
            <Link
              to="/register"
              className="font-medium text-blue-700 hover:text-blue-800"
            >
              Register
            </Link>
          </p>
        </div>
        <form onSubmit={handleSubmit} className="mt-8 space-y-6">
          <div className="-space-y-px">
            <div className="my-5">
              <label
                htmlFor="username"
                className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
              >
                Username
              </label>
              <input
                type="text"
                onChange={handleChange}
                placeholder="Username"
                id="username"
                name="username"
                value={data.username}
                className={fixedInputClass}
              />
            </div>
            <div className="my-5">
              <label
                htmlFor="password"
                className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
              >
                Password
              </label>
              <input
                type="password"
                onChange={handleChange}
                placeholder="Password"
                id="password"
                name="password"
                value={data.password}
                className={fixedInputClass}
              />
            </div>
          </div>
          <button
            type="submit"
            className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-md font-medium rounded-md text-white bg-blue-700 hover:bg-blue-800"
          >
            Login
          </button>
          {/* <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-blue-gray-600 after:mt-0.5 after:flex-1 after:border-t after:border-blue-gray-600">
            <p className="mx-4 mb-0 text-center font-normal text-blue-gray-600">
              OR
            </p>
          </div>
          <Button
            size="md"
            variant="outlined"
            className="w-full flex justify-center items-center gap-3 normal-case text-md font-medium font-Outfit text-blue-gray-900"
            onClick={() => {
              setGoogleLogin(true);
              login();
            }}
          >
            <img
              src="../../src/assets/images/search.png"
              alt="metamask"
              className="h-5 w-5 mr-2"
            />
            Continue with Google
          </Button> */}
        </form>
      </div>
    </div>
  );
}
