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

public class sellerRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop", "root", "Ajay@123");

            String sql = "INSERT INTO sellers (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();
            conn.close();

            // Set response type
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Display success message and link to login page
            out.println("<html><body>");
            out.println("<h2>Registration successful!</h2>");
            out.println("<p>Please <a href='sellerlogin.html'>login</a> to continue.</p>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }
}
