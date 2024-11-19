import axios from "axios";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./Layout";
import Assessment from "./pages/Assessment";
import Error from "./pages/Error";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Results from "./pages/Results";
import Summary from "./pages/Summary";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <Error />,
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "login",
        element: <Login />,
      },
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "assessment",
        element: <Assessment />,
      },
      {
        path: "results",
        element: <Results />,
      },
      {
        path: "summary/:sid",
        element: <Summary />,
        // loader: async () => {
        //   console.log(sid);
        //   const response = await axios.get(`/response/get/${sid}`);
        //   console.log(response.data);
        //   return response.data;
        // },
      },
    ],
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
