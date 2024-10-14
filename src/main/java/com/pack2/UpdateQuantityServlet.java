package com.pack2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/UpdateQuantityServlet")
public class UpdateQuantityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getParameter("productId");
        String action = request.getParameter("action");

        // Validate and parse productId
        int productId = Integer.parseInt(productIdStr); // Convert to int
        int quantityChange = action != null && action.equals("increase") ? 1 : -1;

        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null) {
            for (Product product : cart) {
                if (product.getProductId() == productId) { // Compare as int
                    int newQuantity = product.getQuantity() + quantityChange;

                    // Ensure the quantity does not go below zero
                    if (newQuantity < 0) newQuantity = 0;
                    product.setQuantity(newQuantity);
                    break; // Exit loop after updating
                }
            }
            session.setAttribute("cart", cart); // Update the session cart
        }

        // Optionally, recalculate total prices here if necessary

        // Redirect back to the cart page
        response.sendRedirect("cart.jsp");
    }
}
