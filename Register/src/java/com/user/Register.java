package com.user;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
@MultipartConfig
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException, ClassNotFoundException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Step 1: Get form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Part part = request.getPart("image");
        String filename = part.getSubmittedFileName();
        out.println(filename);

       
        // Step 2: Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/Register"; // 🔁 Change this
        String dbUser = "root";                                        // 🔁 Your DB user
        String dbPassword = "Umesh@123";                           // 🔁 Your DB password

        // Step 3: Load JDBC driver
        
        Class.forName("com.mysql.cj.jdbc.Driver");

        try {
            
             Thread.sleep(1500);
            // Step 4: Connect to database
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            
            // Step 5: Insert data
            String sql = "INSERT INTO users (username, email, password,imageName) VALUES (?, ?, ? , ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, filename);
            
            stmt.executeUpdate();
            // uplode data
            InputStream is = part.getInputStream();
            byte[]data = new byte[is.available()];
            is.read(data);
            String path = request.getRealPath("/")+"img"+File.separator+filename;
            out.println(path);
            FileOutputStream fos = new FileOutputStream(path);
            
            fos.write(data);
            fos.close();
            out.println("done");

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                out.println("<h1>Registration Successful!</h1>");
            } else {
                out.println("<h1>Registration Failed!</h1>");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<h1>Database Error: " + e.getMessage() + "</h1>");
        } catch (InterruptedException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Error: JDBC Driver not found.</h1>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Error: JDBC Driver not found.</h1>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet that handles user registration";
    }
}
