<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <div class="form-container">
        <h2 class="text-center">Delete User</h2>

        <c:if test="${not empty successMessage}">
            <p class="text-success text-center">${successMessage}</p>
        </c:if>

        <c:if test="${not empty error}">
            <p class="text-danger text-center">${error}</p>
        </c:if>

        <form action="deleteUser" method="get">
            <div class="form-group">
                <label for="email">Enter Email to Delete:</label>
                <input type="email" id="email" name="email" class="form-control" required>
            </div>

            <div class="text-center mt-3">
                <input type="submit" value="Delete" class="btn btn-danger">
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
