<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background: url('https://cdn.pixabay.com/photo/2022/12/03/22/10/nature-7633454_1280.jpg') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .form-container {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .btn-custom {
            background-color: #007bff;
            color: white;
            width: 100%;
        }

        .btn-custom:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2 class="text-center">Forgot Password</h2>

    <!-- Display error message if password reset fails -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- Display success message if password reset is successful -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <form action="forgotPassword" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Enter your Email</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label for="newPassword" class="form-label">New Password</label>
            <input type="password" id="newPassword" name="newPassword" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-custom">Reset Password</button>
    </form>

    <div class="text-center mt-3">
        <a href="signin.jsp">Back to Login</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
