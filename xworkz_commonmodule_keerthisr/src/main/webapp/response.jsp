<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        body {
            background-color: pink;
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }

        h1 {
            font-size: 36px;
            font-weight: bold;
            color: green;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        h3 {
            font-size: 24px;
            font-weight: bold;
            color: blue;
            margin: 20px 0;
        }

        h4 {
            color: red;
            font-size: 18px;
            font-weight: bold;
        }

        marquee {
            font-size: 20px;
            font-weight: bold;
            color: purple;
            width: 80%;
        }
    </style>
</head>
<body>

    <h1>Thank you ${ name } for Registering!!</h1>

    <!-- Marquee Effect -->
    <h3>
        <marquee behavior="scroll" direction="left" scrollamount="8">
            Welcome to Our Platform! We are excited to have you on board.
        </marquee>
    </h3>

    <h4>${errors}</h4>

</body>
</html>
