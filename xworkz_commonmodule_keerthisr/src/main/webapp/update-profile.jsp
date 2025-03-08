<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736884_1280.jpg') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .form-container {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            width: 500px;
        }

        .navbar {
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .navbar-brand img {
            height: 50px;
        }

        label {
            font-weight: bold;
        }

        .error-message {
            color: red;
            font-size: 12px;
        }
    </style>
</head>
<body>

<!-- ✅ Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="https://www.x-workz.in/Logo.png" alt="Logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="welcome.jsp">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout.jsp">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- ✅ Update Profile Form -->
<div class="container mt-5">
    <div class="form-container">
        <h2 class="text-center">Update Profile</h2>

        <form action="updateUser" method="post">
            <input type="hidden" name="email" value="${loggedInUser.email}" />

            <div class="mb-3">
                <label>Name:</label>
                <input type="text" name="name" class="form-control" value="${loggedInUser.name}" required />
            </div>

            <div class="mb-3">
                <label>Phone:</label>
                <input type="text" name="phoneNumber" class="form-control" value="${loggedInUser.phoneNumber}" required />
            </div>

            <div class="mb-3">
                <label>Location:</label>
                <select id="location" name="location" class="form-control" required>
                    <option value="" disabled>Select Location</option>
                    <option value="Bangalore" ${loggedInUser.location == 'Bangalore' ? 'selected' : ''}>Bangalore</option>
                    <option value="Shivamogga" ${loggedInUser.location == 'Shivamogga' ? 'selected' : ''}>Shivamogga</option>
                    <option value="Mysore" ${loggedInUser.location == 'Mysore' ? 'selected' : ''}>Mysore</option>
                    <option value="Hasan" ${loggedInUser.location == 'Hasan' ? 'selected' : ''}>Hasan</option>
                    <option value="Ramnagara" ${loggedInUser.location == 'Ramnagara' ? 'selected' : ''}>Ramnagara</option>
                    <option value="Chithradurga" ${loggedInUser.location == 'Chithradurga' ? 'selected' : ''}>Chithradurga</option>
                </select>
            </div>

            <div class="mb-3">
                <label>Age:</label>
                <input type="text" name="age" class="form-control" value="${loggedInUser.age}" />
            </div>

            <div class="mb-3">
                <label>New Password:</label>
                <input type="password" name="password" class="form-control" />
            </div>

            <div class="text-center">
                <input type="submit" value="Update Profile" class="btn btn-primary">
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
