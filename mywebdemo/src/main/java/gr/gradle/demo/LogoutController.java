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

public class LogoutController extends HttpServlet {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		//Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
		  String token =  request.getHeader("X-OBSERVATORY-AUTH");//headers.getFirstValue("X-OBSERVATORY-AUTH");
		  //System.out.println(token);
				
		if (dataAccess.logout(token))
			request.setAttribute("name", "successfull logout");
		else 
			request.setAttribute("name", "you trolling me boy?");
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
