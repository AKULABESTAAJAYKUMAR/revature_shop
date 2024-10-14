package com.pack2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get product details from the request
        String productId = request.getParameter("product_id");
        String productName = request.getParameter("product_name");
        String productPrice = request.getParameter("product_price");
        String productImage = request.getParameter("product_image");

        // Validate inputs
        if (productId == null || productId.isEmpty() || 
            productName == null || productName.isEmpty() || 
            productPrice == null || productPrice.isEmpty() || 
            productImage == null || productImage.isEmpty()) {
            response.sendRedirect("errorPage.jsp");
            return;
        }

        Product product = new Product();
        try {
            product.setProductId(Integer.parseInt(productId));
            product.setProductName(productName);
            product.setProductPrice(Double.parseDouble(productPrice));
            product.setProductImage(productImage);
            product.setQuantity(1); // Default quantity when added to cart
        } catch (NumberFormatException e) {
            response.sendRedirect("errorPage.jsp");
            return;
        }

        // Retrieve the cart from the session
        HttpSession session = request.getSession();
        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        // Add the product to the cart
        cart.add(product);
        session.setAttribute("cart", cart);

        // Redirect to the cart page
        response.sendRedirect("cart.jsp");
    }
}
