package gr.gradle.demo;
import java.util.Random;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.conf.Configuration;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginController
 */

public class LoginController extends HttpServlet {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		String newtoken = this.createToken();
		if (dataAccess.login(un, pw, newtoken))
			request.setAttribute("name", "this is your token:"+newtoken);
		else 
			request.setAttribute("name", "you trolling me nigga?");
		request.getRequestDispatcher("response.jsp").forward(request, response);
		return;
	}
	private String createToken(){
		Random r = new Random();

		String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
		String new_token = "";
    	for (int i = 0; i < 25; i++) {
        	new_token += (alphabet.charAt(r.nextInt(alphabet.length())));
    	}  
		return new_token;
	}

}
