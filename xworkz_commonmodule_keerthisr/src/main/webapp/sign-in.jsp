<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="signin-validation.js"></script>

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
    <script>
        function checkName(){
            var checkValue =document.getElementById('name').value;
            console.log(checkValue);
            if(checkValue !== ""){
                var xhttp = new XMLHttpRequest();
                xhttp.open("GET", "http://localhost:8080/xworkz_commonmodule_keerthisr/checkValue/"+checkValue);
                xhttp.send();
                xhttp.onload = function(){
                      console.log(this.responseText)
                      document.getElementById("nameError").innerHTML = this.responseText;
                }
            }
        }
        function checkEmail() {
                    var checkValue = document.getElementById('email').value.trim();
                    console.log(checkValue);

                    const emailRegex = /^[a-zA-Z0-9._]+@gmail\.com$/; // Only allows @gmail.com emails
                    const emailErrorElement = document.getElementById("emailError");

                    if (!emailRegex.test(checkValue)) {
                        emailErrorElement.innerHTML = "Invalid Email: Email format like (e.g., example@gmail.com) are allowed.";
                        emailErrorElement.style.display = "block";
                        return;
                    } else {
                        emailErrorElement.style.display = "none";
                    }

                    const xhttp = new XMLHttpRequest();
                    xhttp.open("GET", "http://localhost:8080/xworkz_commonmodule_keerthisr/email/" + encodeURIComponent(checkValue));
                    xhttp.send();

                    xhttp.onload = function () {
                        emailErrorElement.innerHTML = this.responseText;
                        emailErrorElement.style.display = this.responseText ? "block" : "none";
                    };
                }
                document.getElementById("email").addEventListener("input", function () {
                    const emailErrorElement = document.getElementById("emailError");
                    emailErrorElement.style.display = /^[a-zA-Z0-9._]+@gmail\.com$/.test(this.value.trim()) ? "none" : "block";
                });

        function checkPhoneNumber() {
            var checkValue = document.getElementById('phoneNumber').value.trim();
            console.log(checkValue);

            const phoneRegex = /^[6-9]\d{9}$/; // Ensures 10 digits starting with 6, 7, 8, or 9
            const phoneErrorElement = document.getElementById("phoneError");

            if (!phoneRegex.test(checkValue)) {
                phoneErrorElement.innerHTML = "Invalid Phone Number: Must be 10 digits & start with 6-9.";
                phoneErrorElement.style.display = "block";
                return;
            } else {
                phoneErrorElement.style.display = "none";
            }

            const xhttp = new XMLHttpRequest();
            xhttp.open("GET", "http://localhost:8080/xworkz_commonmodule_keerthisr/phone/" + encodeURIComponent(checkValue));
            xhttp.send();

            xhttp.onload = function () {
                phoneErrorElement.innerHTML = this.responseText;
                phoneErrorElement.style.display = this.responseText ? "block" : "none";
            };
        }

            document.getElementById("phoneNumber").addEventListener("input", function () {
                const phoneErrorElement = document.getElementById("phoneError");
                phoneErrorElement.style.display = /^[6-9]\d{9}$/.test(this.value.trim()) ? "none" : "block";
        });

    </script>
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

<div class="container mt-5">
    <div class="form-container">
        <h2 class="text-center">User Registration</h2>

        <c:if test="${not empty successMessage}">
            <p class="text-success text-center">${successMessage}</p>
        </c:if>

        <c:if test="${not empty error}">
            <p class="error-message text-center">${error}</p>
        </c:if>

        <form action="addUser" method="post">
            <div class="row">
                <div class="col-md-6 form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" class="form-control" value="${name}" onchange="checkName()" required>
                    <span id = "nameError" style = "color: red"></span>
                    <c:if test="${not empty nameError}">
                        <p class="error-message">${nameError}</p>
                    </c:if>
                </div>

                <div class="col-md-6 form-group">
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email" class="form-control" value="${email}" onchange="checkEmail()" required>
                    <span id = "emailError" style = "color: red"></span>
                    <c:if test="${not empty emailError}">
                        <p class="error-message">${emailError}</p>
                    </c:if>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" value="${phoneNumber}" onchange="checkPhoneNumber()" required>
                    <span id = "phoneError" style = "color: red"></span>
                    <c:if test="${not empty phoneError}">
                        <p class="error-message">${phoneError}</p>
                    </c:if>
                </div>

                <div class="col-md-6 form-group">
                    <label for="location">Location:</label>
                    <select id="location" name="location" class="form-control" required>
                        <option value="" disabled selected>Select Location</option>
                        <option value="Bangalore">Bangalore</option>
                        <option value="Shivamogga">Shivamogga</option>
                        <option value="Mysore">Mysore</option>
                        <option value="Hasan">Hasan</option>
                        <option value="Ramnagara">Ramnagara</option>
                        <option value="Chithradurga">Chithradurga</option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label for="gender">Gender:</label>
                    <select id="gender" name="gender" class="form-control" required>
                        <option value="" disabled selected>Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>

                <div class="col-md-6 form-group">
                    <label for="dOB">Date of Birth:</label>
                    <input type="date" id="dOB" name="dOB" class="form-control" required>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 form-group">
                    <label for="age">Age:</label>
                    <input type="text" id="age" name="age" class="form-control" value="${age}" required>
                    <c:if test="${not empty ageError}">
                        <p class="error-message">${ageError}</p>
                    </c:if>
                </div>
            </div>

            <div class="text-center mt-3">
                <input type="submit" value="SUBMIT" class="btn btn-success">
            </div>
        </form>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
