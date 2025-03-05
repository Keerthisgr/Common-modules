<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@ page isELIgnored="false" %>
<! DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
    body {
    background -color :pink ;
    }
    h1{
    text-align:center;
    font-size:36px;
    font-weight:bold;
    color:green;
    animation:rotate 10s infinite;
    }
    </style>
</head>
<body>
 <h1>Thank you  ${ name } for Registering!! </h1>
<h4 align="center">${errors}</h4>

</body>
</html>