package gr.gradle.demo;

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

public class LoginController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String un=request.getParameter("username");
		String pw=request.getParameter("password");

		// Connect to mysql and verify username password

		try {
			Class.forName("com.mysql.jdbc.Driver");
		 // loads driver
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection

		PreparedStatement ps = c.prepareStatement("select admin from users where username=? and password=?");
		ps.setString(1, un);
		ps.setString(2, pw);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			String admin = Integer.toString(rs.getInt("admin"));

			request.setAttribute("name", admin);
        	request.getRequestDispatcher("response.jsp").forward(request, response);
        	return;
		}
		request.setAttribute("name", "ERROR");
        request.getRequestDispatcher("response.jsp").forward(request, response);
        return;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
