<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            text-align: center;
            padding: 50px;
        }
        .error-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            display: inline-block;
        }
        .error-message {
            color: #721c24;
            font-size: 18px;
            font-weight: bold;
        }
        .back-link {
            display: block;
            margin-top: 20px;
            color: #0056b3;
            text-decoration: none;
            font-size: 16px;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <p class="error-message">${error}</p>
        <a href="index.jsp" class="back-link">Go Back</a>
    </div>
</body>
</html>