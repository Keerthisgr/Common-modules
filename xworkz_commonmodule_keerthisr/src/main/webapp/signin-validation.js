document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("form").addEventListener("submit", function (event) {
        let isValid = true;

        // Name validation
        let name = document.getElementById("name").value;
        let nameError = document.getElementById("nameError");
        let namePattern = /^[A-Z][a-zA-Z ]{2,49}$/;
        if (!namePattern.test(name)) {
            nameError.textContent = "Invalid name format.";
            isValid = false;
        } else {
            nameError.textContent = "";
        }

        // Email validation
        let email = document.getElementById("email").value;
        let emailError = document.getElementById("emailError");
        let emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        if (!emailPattern.test(email)) {
            emailError.textContent = "Invalid email format.";
            isValid = false;
        } else {
            emailError.textContent = "";
        }

        // Phone validation
        let phoneNumber = document.getElementById("phoneNumber").value;
        let phoneError = document.getElementById("phoneError");
        let phonePattern = /^[9876]\d{9}$/;
        if (!phonePattern.test(phoneNumber)) {
            phoneError.textContent = "Invalid phone number format.";
            isValid = false;
        } else {
            phoneError.textContent = "";
        }

        // Age validation
        let age = document.getElementById("age").value;
        let ageError = document.getElementById("ageError");
        if (isNaN(age) || age < 18 || age > 95) {
            ageError.textContent = "Age must be between 18 and 95.";
            isValid = false;
        } else {
            ageError.textContent = "";
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});
