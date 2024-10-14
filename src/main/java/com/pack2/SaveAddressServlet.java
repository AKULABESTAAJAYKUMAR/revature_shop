package com.pack2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SaveAddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the address details from the form
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal_code");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop", "root", "Ajay@123");

            // Insert the address into the database
            String sql = "INSERT INTO addresses (name, address, city, postal_code) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, city);
            preparedStatement.setString(4, postalCode);

            // Execute the query to insert the data
            int rowsAffected = preparedStatement.executeUpdate();

            // Set response type
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Check if the address was successfully saved
            if (rowsAffected > 0) {
                // Redirect to payment.html after successfully saving the address
                out.println("<html><body>");
                out.println("<h2>Address saved successfully!</h2>");
                out.println("<p>Proceed to <a href='payment.html'>Payment</a>.</p>");
                out.println("</body></html>");
            } else {
                out.println("Failed to save address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
