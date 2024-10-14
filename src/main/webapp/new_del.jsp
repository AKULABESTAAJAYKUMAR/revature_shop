<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Buyer Registration - Revature Shop</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }

        .header-container {
            position: relative;
            width: 100%;
            height: 100vh;
        }

        .header-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .header-container h1 {
            position: absolute;
            top: 10%;
            left: 50%;
            transform: translate(-50%, -50%);
            color: red;
            font-size: 36px;
            font-family: Arial, sans-serif;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.6);
        }

        .form-container {
            position: absolute;
            top: 20%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 10px;
        }

        .button {
            padding: 10px 20px;
            font-size: 18px;
            font-family: Arial, sans-serif;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .button:hover {
            background-color: #0056b3;
        }

        input {
            padding: 10px;
            margin: 10px 0;
            width: 100%;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="header-container">
        <h1>Buyer Registration</h1>
        <img src="https://tse2.mm.bing.net/th?id=OIG1.adV2tJyy27mJ4JUur0CM&pid=ImgGn" alt="Revature Shop">
        <div class="form-container">
            <form action="sellerServlet" method="POST"> <!-- Ensure this matches the servlet URL pattern -->
                <input type="text" name="name" placeholder="Enter your name" required>
                <input type="email" name="email" placeholder="Enter your email" required>
                <input type="password" name="password" placeholder="Enter your password" required>
                <button type="submit" class="button">Register</button>
            </form>
        </div>
    </div>
</body>
</html>

</head>
<body>

</body>
</html>