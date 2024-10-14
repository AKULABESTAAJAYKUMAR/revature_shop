package com.pack2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class RemoveProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null && productIdStr != null) {
            int productId = Integer.parseInt(productIdStr); // Convert String to int
            boolean isRemoved = cart.removeIf(product -> product.getProductId() == productId); // Remove the product by ID
            
            if (isRemoved) {
                session.setAttribute("message", "Product removed successfully.");
            } else {
                session.setAttribute("message", "Product not found in the cart.");
            }
            
            session.setAttribute("cart", cart); // Update the session cart
        } else {
            session.setAttribute("message", "No products in cart to remove.");
        }

        response.sendRedirect("cart.jsp"); // Redirect to the shopping cart page
    }
}
