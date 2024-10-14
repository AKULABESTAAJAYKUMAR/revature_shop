package com.pack2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve product details from the form
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String productImage = request.getParameter("productImage");
        String productDescription = request.getParameter("productDescription");

        // Validate inputs
        if (productName == null || productPrice == null || productImage == null || productDescription == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input");
            return;
        }

        // Debugging output
        System.out.println("Attempting to insert product: " + productName);

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish database connection
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop?serverTimezone=UTC", "root", "Ajay@123");
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (name, price, image_url, description) VALUES (?, ?, ?, ?)")) {

                // Ensure price is stored as double
                double price;
                try {
                    price = Double.parseDouble(productPrice);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid price format");
                    return;
                }

                // Set parameters for the prepared statement
                preparedStatement.setString(1, productName);
                preparedStatement.setDouble(2, price);
                preparedStatement.setString(3, productImage);
                preparedStatement.setString(4, productDescription);

                int rowsAffected = preparedStatement.executeUpdate();
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();

                if (rowsAffected > 0) {
                    // If insertion is successful, send a confirmation response
                    out.println("<html><body>");
                    out.println("<h2>Product added successfully!</h2>");
                    out.println("<p><a href='buyerfrontpage.html'>View Products</a></p>");
                    out.println("<p><a href='sellerfrontpage.html'>Add Another Product</a></p>");
                    out.println("</body></html>");
                } else {
                    out.println("<html><body><h2>Failed to add product.</h2></body></html>");
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error saving product data: " + e.getMessage());
            System.out.println("Error saving product data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("JDBC Driver not found: " + e.getMessage());
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }
}
