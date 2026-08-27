package com.empresa.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/registrar")
public class RegistroServlet extends HttpServlet {

    private String url = "jdbc:mysql://localhost:3306/empresa_db?useSSL=false&serverTimezone=UTC";
    private String user = "root";
    private String pass = "tu_password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        String sql = "INSERT INTO usuarios (nombre, correo, password) VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, nombre);
                stmt.setString(2, correo);
                stmt.setString(3, password);

                stmt.executeUpdate();
                request.setAttribute("usuarioNombre", nombre);
                request.getRequestDispatcher("exito.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
