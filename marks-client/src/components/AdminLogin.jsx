import React, { useState } from "react";
import axios from "axios";

const AdminLogin = ({ setIsAdmin }) => {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const handleAdminLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        credentials,
      );
      const { token, role } = response.data;

      if (role === "ADMIN") {
        localStorage.setItem("adminToken", token);
        setIsAdmin(true);
      } else {
        alert("Access denied. Admin only.");
      }
    } catch (err) {
      alert("Invalid admin credentials");
    }
  };

  return (
    <div className="flex items-center justify-center h-screen bg-blue-100">
      <form
        onSubmit={handleLogin}
        className="p-8 bg-white shadow-lg rounded-lg w-96"
      >
        <h2 className="text-2xl font-bold mb-6 text-center">Educator Login</h2>

        <input
          type="text"
          placeholder=" admin username"
          className="w-full p-2 mb-4 border rounded"
          onChange={(e) =>
            setCredentials({ ...credentials, username: e.target.value })
          }
        />

        <input
          type="password"
          placeholder=" admin password"
          className="w-full p-2 mb-6 border rounded"
          onChange={(e) =>
            setCredentials({ ...credentials, password: e.target.value })
          }
        />

        <button className="w-full bg-blue-500 text-white p-2 rounded font-bold hover:bg-blue-700">
          Login
        </button>
      </form>
    </div>
  );
};
