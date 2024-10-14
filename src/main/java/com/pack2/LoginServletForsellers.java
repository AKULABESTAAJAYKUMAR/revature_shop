package com.pack2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServletForsellers extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/revature_shop", "root", "Ajay@123");

            // SQL query to check if the user exists with the given email and password
            String sql = "SELECT * FROM sellers WHERE email = ? AND password = ?";
            PreparedStatement statement = c1.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (resultSet.next()) {
                // Login successful, redirect to frontpage.html
                response.sendRedirect("sellerfrontpage.html");
            } else {
                // Login failed, show error message
                out.println("<h2>Login failed! Invalid email or password.</h2>");
                out.println("<a href='sellerlogin.html'>Try Again</a>");
            }

            c1.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }
}
