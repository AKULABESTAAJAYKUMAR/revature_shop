package com.pack2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String name = request.getParameter("name");
        String rating = request.getParameter("rating");
        String reviewText = request.getParameter("reviewText");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop", "root", "Ajay@123");

            // Insert the review into the database
            String sql = "INSERT INTO reviews (product_id, product_name, reviewer_name, rating, review_text) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productId);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, rating);
            preparedStatement.setString(5, reviewText);

            int rowsAffected = preparedStatement.executeUpdate();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Path to the file where you want to store the reviews
            String filePath = getServletContext().getRealPath("/") + "r.html";

            // Write the review data to the HTML file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write("<div class='review'>");
                writer.write("<h5>" + productName + " (ID: " + productId + ") - " + rating + " Stars</h5>");
                writer.write("<p>Reviewed by: " + name + "</p>");
                writer.write("<p>" + reviewText + "</p>");
                writer.write("</div><hr>");
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error writing to file: " + e.getMessage());
                return;
            }

            if (rowsAffected > 0) {
                out.println("<html><body>");
                out.println("<h2>Review submitted successfully!</h2>");
                out.println("<p>Thank you for your feedback!</p>");
                out.println("<p><a href='r.html'>View Reviews</a></p>");
                out.println("<p><a href='buyerfrontpage.html'>Home</a></p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body><h2>Failed to submit review.</h2></body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
