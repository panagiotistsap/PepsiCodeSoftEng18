package com.candidjava;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(name = "VoluLoginController", urlPatterns = {"VoluLoginController"}, loadOnStartup = 1)
public class VoluLoginController extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String un=request.getParameter("username");
		String pw=request.getParameter("password");
		
		// Connect to mysql and verify username password
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		 // loads driver
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "myuser", "pass"); // gets a new connection
 
		PreparedStatement ps = c.prepareStatement("select username,password from volunteers where username=? and password=?");
		ps.setString(1, un);
		ps.setString(2, pw);
 
		ResultSet rs = ps.executeQuery();
 
		while (rs.next()) {
			request.setAttribute("name", "Welcome");
        	request.getRequestDispatcher("/loginjsps/hello.jsp").forward(request, response);
        	return;
		}
		request.setAttribute("name", "ERROR");
        request.getRequestDispatcher("/loginjsps/hello.jsp").forward(request, response);
        return;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
