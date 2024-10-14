<%@ page import="java.util.List" %>
<%@ page import="com.pack2.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Shopping Cart</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button {
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .button:hover {
            background-color: #45a049;
        }
        img {
            border-radius: 5px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }
        .total {
            font-weight: bold;
            font-size: 1.2em;
            text-align: center;
            margin-top: 20px;
        }
        .favorite-icon {
            cursor: pointer;
            font-size: 24px; /* Adjust size as needed */
        }
        .favorite-icon.favorited {
            color: red; /* Change to full red when favorited */
            transition: color 0.3s ease; /* Smooth transition effect */
        }
    </style>
</head>
<body>
    <h1>Your Shopping Cart</h1>
    <table>
        <tr>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total Price</th>
            <th>Image</th>
            <th>Action</th>
            <th>Favorite</th> <!-- Added Favorite column -->
        </tr>
        <%
            // Retrieve the cart from the session
            List<Product> cart = (List<Product>) session.getAttribute("cart");
            double grandTotal = 0; // Initialize grand total

            if (cart != null && !cart.isEmpty()) {
                for (Product product : cart) {
                    double totalPrice = product.getProductPrice() * product.getQuantity(); // Calculate total price for the product
                    grandTotal += totalPrice; // Add to grand total

                    out.println("<tr>");
                    out.println("<td>" + product.getProductName() + "</td>");
                    out.println("<td>");
                    out.println("<form action='UpdateQuantityServlet' method='POST' style='display:inline;'>");
                    out.println("<input type='hidden' name='productId' value='" + product.getProductId() + "' />");
                    out.println("<button class='button' type='submit' name='action' value='decrease'>-</button>");
                    out.println("<input type='text' name='quantity' value='" + product.getQuantity() + "' style='width: 40px; text-align: center;' readonly />");
                    out.println("<button class='button' type='submit' name='action' value='increase'>+</button>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("<td>₹" + product.getProductPrice() + "</td>");
                    out.println("<td>₹" + totalPrice + "</td>"); // Display total price for the product
                    out.println("<td><img src='" + product.getProductImage() + "' alt='Product Image' width='100'/></td>");
                    out.println("<td>");
                    out.println("<form action='RemoveProductServlet' method='POST' style='display:inline;'>");
                    out.println("<input type='hidden' name='productId' value='" + product.getProductId() + "' />");
                    out.println("<button class='button' type='submit'>Remove</button>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("<td>"); // Added cell for Favorite icon
                    out.println("<span class='favorite-icon' onclick='toggleFavorite(this, " + product.getProductId() + ", \"" + product.getProductName() + "\", " + product.getProductPrice() + ", \"" + product.getProductImage() + "\")'>");
                    out.println("<i class='bi bi-heart'></i>");
                    out.println("</span>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr><td colspan='7'>Your cart is empty.</td></tr>");
            }
        %>
    </table>

    <% if (cart != null && !cart.isEmpty()) { %>
        <div class="total">Grand Total: ₹<%= grandTotal %></div> <!-- Display grand total -->
        <div class="button-container">
            <form action="buyeraddress.html" method="GET" style="display:inline;">
                <button class="button" type="submit">Proceed to Checkout</button>
            </form>
            
            <form action="buyerfrontpage.html" method="GET" style="display:inline;">
                <button class="button" type="submit">Home</button>
            </form>
        </div>
    <% } %>
</body>
<script>
    function toggleFavorite(element, id, name, price, image) {
        const heartIcon = element.querySelector('i'); // Get the heart icon

        // Check if the product is already favorited
        if (element.classList.toggle("favorited")) {
            heartIcon.classList.remove("bi-heart");
            heartIcon.classList.add("bi-heart-fill");

            // Store product details in localStorage
            const favoriteProduct = {
                id: id,
                name: name,
                price: price,
                image: image
            };
            localStorage.setItem('favoriteProduct', JSON.stringify(favoriteProduct));

            // Redirect to favorites page
            window.location.href = 'favourite.html';
        } else {
            heartIcon.classList.remove("bi-heart-fill");
            heartIcon.classList.add("bi-heart");
            // Optionally, remove from localStorage or handle unfavorite logic
        }

        // Log the favorites to the console
        console.log("Favorites:", JSON.parse(localStorage.getItem('favoriteProduct')));
    }
</script>
</html>
