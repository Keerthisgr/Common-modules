<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://cdn.pixabay.com/photo/2025/02/22/14/30/trees-9424194_1280.jpg') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
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
            margin-top: 5px;
        }

        .register-link {
            display: block;
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
        }

        .register-link a {
            color: blue;
            text-decoration: none;
            font-weight: bold;
        }

        .register-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>


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

            </ul>
        </div>
    </div>
</nav>

<!-- Sign-In Form -->
<div class="form-container">
    <h2 class="text-center">Sign In</h2>


    <c:if test="${not empty error}">
        <p class="error-message text-center">${error}</p>
    </c:if>

    <form action="signIn" method="post">
        <div class="mb-3">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" class="form-control" value="${param.email}" required>
            <c:if test="${not empty emailError}">
                <p class="error-message">${emailError}</p>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required>
            <c:if test="${not empty passwordError}">
                <p class="error-message">${passwordError}</p>
            </c:if>
        </div>
        <div class="text-center">
            <input type="submit" value="SIGN IN" class="btn btn-primary">
        </div>
    </form>


    <p class="register-link">Not registered? <a href="sign-in.jsp">Register here</a></p>
     <p class="register-link">Forgot password? <a href="forgot-password.jsp">Create new password</a></p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
