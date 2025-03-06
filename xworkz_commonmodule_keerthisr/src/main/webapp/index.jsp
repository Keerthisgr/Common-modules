<%@ page language="java" contentType="text/html; charset=US-ASCII"
       pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Index Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: url('https://cdn.pixabay.com/photo/2023/11/26/04/26/dawn-8412840_1280.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .navbar-brand img {
            height: 50px;
        }
         .animated-text {
                    font-size: 2rem;
                    font-weight: bold;
                    opacity: 0;
                    text-align: center;
                    color: #ff;
                    animation: fadeIn 3s ease-in-out forwards;
                }
                @keyframes fadeIn {
                    0% { opacity: 0; transform: translateY(-20px); }
                    100% { opacity: 1; transform: translateY(0); }
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
                    <li class="nav-item">
                        <a class="nav-link" href="sign-in.jsp">Sign Up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="signin.jsp">Sign In</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
 <div class="animated-text">Welcome to the beginning of something amazing!</div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

