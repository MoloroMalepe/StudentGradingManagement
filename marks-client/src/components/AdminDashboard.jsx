import React, { useState } from "react";
import axios from "axios";

const AdminDashboard = () => {
  const [newTeacher, setNewTeacher] = useState({
    username: "",
    password: "",
    email: "",
  });
  const [teachers, setTeachers] = useState([]);

  // Get token from localStorage
  const token = localStorage.getItem("adminToken");

  // Fetch existing teachers
  const fetchTeachers = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/admin/teachers",
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      setTeachers(response.data);
    } catch (err) {
      console.error("Failed to fetch teachers");
    }
  };

  // Create new teacher
  const handleCreateTeacher = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "http://localhost:8080/api/admin/create-teacher",
        newTeacher,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      alert("Teacher account created successfully!");
      setNewTeacher({ username: "", password: "", email: "" });
      fetchTeachers(); // Refresh list
    } catch (err) {
      alert(err.response?.data || "Failed to create account");
    }
  };

  // Generate random password
  const generatePassword = () => {
    const chars =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    let password = "";
    for (let i = 0; i < 10; i++) {
      password += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    setNewTeacher({ ...newTeacher, password });
  };

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-6">Admin Dashboard</h1>

      {/* Create Teacher Form */}
      <div className="bg-white p-6 rounded-lg shadow-md mb-8">
        <h2 className="text-xl font-semibold mb-4">Create Teacher Account</h2>
        <form onSubmit={handleCreateTeacher}>
          <div className="grid grid-cols-3 gap-4 mb-4">
            <input
              type="text"
              placeholder="Username"
              className="p-2 border rounded"
              value={newTeacher.username}
              onChange={(e) =>
                setNewTeacher({ ...newTeacher, username: e.target.value })
              }
              required
            />
            <div className="flex">
              <input
                type="text"
                placeholder="Password"
                className="p-2 border rounded flex-grow"
                value={newTeacher.password}
                onChange={(e) =>
                  setNewTeacher({ ...newTeacher, password: e.target.value })
                }
                required
              />
              <button
                type="button"
                onClick={generatePassword}
                className="ml-2 bg-blue-500 text-white px-3 rounded"
              >
                Generate
              </button>
            </div>
            <input
              type="email"
              placeholder="Email"
              className="p-2 border rounded"
              value={newTeacher.email}
              onChange={(e) =>
                setNewTeacher({ ...newTeacher, email: e.target.value })
              }
              required
            />
          </div>
          <button
            type="submit"
            className="bg-green-600 text-white px-4 py-2 rounded font-semibold"
          >
            Create Teacher Account
          </button>
        </form>
      </div>

      {/* Teachers List */}
      <div className="bg-white p-6 rounded-lg shadow-md">
        <h2 className="text-xl font-semibold mb-4">Teacher Accounts</h2>
        <button
          onClick={fetchTeachers}
          className="mb-4 bg-gray-200 px-4 py-2 rounded"
        >
          Refresh List
        </button>
        <table className="w-full border-collapse border">
          <thead>
            <tr className="bg-gray-100">
              <th className="border p-2">Username</th>
              <th className="border p-2">Email</th>
              <th className="border p-2">Created Date</th>
              <th className="border p-2">Status</th>
            </tr>
          </thead>
          <tbody>
            {teachers.map((teacher) => (
              <tr key={teacher.id}>
                <td className="border p-2">{teacher.username}</td>
                <td className="border p-2">{teacher.email}</td>
                <td className="border p-2">
                  {new Date(teacher.createdAt).toLocaleDateString()}
                </td>
                <td className="border p-2">
                  <span
                    className={`px-2 py-1 rounded ${
                      teacher.isActive
                        ? "bg-green-100 text-green-800"
                        : "bg-red-100 text-red-800"
                    }`}
                  >
                    {teacher.isActive ? "Active" : "Inactive"}
                  </span>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
