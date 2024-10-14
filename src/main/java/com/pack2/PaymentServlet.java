package com.pack2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve payment details from the form
        String cardName = request.getParameter("card_name");
        String cardNumber = request.getParameter("card_number");
        String expiryDate = request.getParameter("expiry_date");
        String cvv = request.getParameter("cvv");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop", "root", "Ajay@123");

            // SQL query to insert payment data
            String insertQuery = "INSERT INTO payments (card_name, card_number, expiry_date, cvv) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, cardName);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.setString(3, expiryDate);
            preparedStatement.setString(4, cvv);
            preparedStatement.executeUpdate();
            conn.close();

            // Log success message in the console
            System.out.println("Payment details stored in the database successfully!");

            // Set response type
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Display success message
            out.println("<html><body>");
            out.println("<h2>Payment successful!</h2>");
            out.println("<p>Your payment has been processed successfully. <a href='orderplaced.html'>Continue</a></p>");
            out.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error saving payment data: " + e.getMessage());

            // Log error in the console
            System.out.println("Error saving payment data: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
    }
}
