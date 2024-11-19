import { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";

const fixedInputClass =
  "rounded-md appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-blue-gray-900 sm:text-md";

export default function Register() {
  const navigate = useNavigate();

  const [data, setData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
    age: 18,
    sex: "Male",
    dob: "",
    profession: "",
    marital_status: "Single",
    address: "",
    contact: 9999999999,
  });

  const handleValidation = () => {
    const {
      username,
      email,
      password,
      confirmPassword,
      age,
      sex,
      dob,
      profession,
      marital_status,
      address,
      contact,
    } = data;

    if (
      username === "" ||
      email === "" ||
      password === "" ||
      confirmPassword === "" ||
      dob === "" ||
      profession === "" ||
      marital_status === "" ||
      address === ""
    ) {
      toast.error("All fields are required");
      return false;
    } else if (username.length < 3) {
      toast.error("Username must be at least 3 characters long");
      return false;
    } else if (!email.includes("@")) {
      toast.error("Email must be valid");
      return false;
    } else if (age < 18) {
      toast.error("You must be at least 18 years old to register");
    } else if (contact.length < 10) {
      toast.error("Invalid phone number entered");
    } else if (password.length < 6) {
      toast.error("Password must be at least 6 characters long");
      return false;
    } else if (password !== confirmPassword) {
      toast.error("Passwords must match");
      return false;
    } else {
      return true;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(data);
    if (handleValidation()) {
      const {
        username,
        email,
        password,
        age,
        sex,
        dob,
        profession,
        marital_status,
        address,
        contact,
      } = data;
      try {
        const userResponse = await axios.post("/user/save", {
          username,
          email,
          password,
          age,
          sex,
          dob,
          profession,
          marital_status,
          address,
          contact,
        });
        console.log(userResponse);
        const credentialResponse = await axios.post("/credentials/save", {
          username,
          password,
        });

        if (userResponse.status === 200) {
          setData({
            username: "",
            email: "",
            password: "",
            confirmPassword: "",
            age: 18,
            sex: "Male",
            dob: "",
            profession: "",
            marital_status: "Single",
            address: "",
            contact: 9999999999,
          });
          toast.success("Registration successful, welcome!");
          navigate("/login");
        } else {
          toast.error(userResponse.message);
        }
      } catch (error) {
        console.log(error);
        toast.error(error.message);
      }
    } else {
      toast.error("An error occurred during registration.");
    }
  };

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  return (
    <div className="h-full flex items-center justify-center px-4 sm:px-6 lg:px-8 font-Outfit">
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
            Signup to create an account
          </h2>
          <p className="mt-5 text-center text-md text-gray-600 ">
            Already have an account?{" "}
            <Link
              to="/login"
              className="font-medium text-blue-700 hover:text-blue-800"
            >
              Login
            </Link>
          </p>
        </div>
        <form onSubmit={handleSubmit} noValidate className="mt-8 space-y-6">
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
              autoComplete="off"
            />
          </div>
          <div className="my-5">
            <label
              htmlFor="email"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Email
            </label>
            <input
              type="email"
              onChange={handleChange}
              placeholder="Email"
              id="email"
              name="email"
              value={data.email}
              className={fixedInputClass}
              autoComplete="off"
            />
          </div>
          <div className="grid gap-6 md:grid-cols-2">
            <div className="my-0">
              <label
                htmlFor="age"
                className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
              >
                Age
              </label>
              <input
                type="number"
                onChange={handleChange}
                placeholder="Age"
                id="age"
                name="age"
                value={data.age}
                className={fixedInputClass}
                autoComplete="off"
              />
            </div>
            <div className="my-0">
              <label
                htmlFor="sex"
                className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
              >
                Sex
              </label>
              <div className="relative">
                <select
                  type="text"
                  onChange={handleChange}
                  placeholder="Sex"
                  id="sex"
                  name="sex"
                  value={data.sex}
                  className={fixedInputClass}
                >
                  <option>Male</option>
                  <option>Female</option>
                  <option>Other</option>
                </select>
              </div>
            </div>
          </div>
          <div className="my-5">
            <label
              htmlFor="dob"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Date of Birth
            </label>
            <input
              type="date"
              onChange={handleChange}
              placeholder="Date of Birth"
              id="dob"
              name="dob"
              value={data.dob}
              className={fixedInputClass}
              autoComplete="off"
            />
          </div>

          <div className="my-5">
            <label
              htmlFor="profession"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Profession
            </label>
            <input
              type="text"
              onChange={handleChange}
              placeholder="Profession"
              id="profession"
              name="profession"
              value={data.profession}
              className={fixedInputClass}
              autoComplete="off"
            />
          </div>

          <div className="my-5">
            <label
              htmlFor="marital_status"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Marital Status
            </label>
            <select
              type="text"
              onChange={handleChange}
              placeholder="Marital Status"
              id="marital_status"
              name="marital_status"
              value={data.marital_status}
              className={fixedInputClass}
            >
              <option>Single</option>
              <option>Married</option>
              <option>Divorced</option>
            </select>
          </div>
          <div className="my-5">
            <label
              htmlFor="address"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Address
            </label>
            <textarea
              type="text"
              onChange={handleChange}
              placeholder="Address"
              id="address"
              name="address"
              value={data.address}
              rows="4"
              className={fixedInputClass}
              autoComplete="off"
            />
          </div>
          <div className="my-5">
            <label
              htmlFor="contact"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Contact Number
            </label>
            <input
              type="number"
              onChange={handleChange}
              placeholder="Contact Number"
              id="contact"
              name="contact"
              value={data.contact}
              className={fixedInputClass}
              autoComplete="off"
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
          <div className="my-5">
            <label
              htmlFor="confirmPassword"
              className="block text-md font-medium leading-6 text-blue-gray-900 mb-1"
            >
              Confirm Password
            </label>
            <input
              type="password"
              onChange={handleChange}
              placeholder="Confirm Password"
              id="confirmPassword"
              name="confirmPassword"
              value={data.confirmPassword}
              className={fixedInputClass}
            />
          </div>
          <button
            type="submit"
            className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-md font-medium rounded-md text-white bg-blue-700 hover:bg-blue-800"
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
}
