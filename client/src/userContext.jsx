/* eslint-disable react/prop-types */
import axios from "axios";
import { createContext, useState, useEffect } from "react";

export const UserContext = createContext();

export const UserContextProvider = ({ children, username }) => {
  const [user, setUser] = useState(null);
  const [sessionId, setSessionId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserAndSession = async () => {
      setLoading(true); // Start loading

      // Return early if username is undefined
      if (!username) {
        setLoading(false);
        return; // Exit the function if no username
      }

      try {
        // Fetch user profile
        const userResponse = await axios.get(`http://localhost:8080/user/get/${username}`, { withCredentials: true });
        console.log(userResponse.data);
        setUser(userResponse.data);

        setLoading(false); // Data fetching complete
      } catch (err) {
        console.error("Error fetching user or session data:", err);
        setError(err);
        setLoading(false); // End loading even on error
      }
    };

    fetchUserAndSession();
  }, [username]); // Update dependency to watch for username prop

  // Track sessionId changes
  useEffect(() => {
    console.log("Updated sessionId:", sessionId);
  }, [sessionId]);

  // Logout function to clear user and sessionId
  const logout = () => {
    setUser(null);
    setSessionId(null);
    console.log("User logged out successfully");
  };

  return (
    <UserContext.Provider value={{ user, setUser, sessionId, logout }}>
      {children}
    </UserContext.Provider>
  );
};
