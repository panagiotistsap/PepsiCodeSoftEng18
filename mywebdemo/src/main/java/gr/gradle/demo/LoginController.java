package gr.gradle.demo;
import java.util.Random;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.conf.Configuration;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.math.BigInteger;
/**
 * Servlet implementation class LoginController
 */

public class LoginController extends HttpServlet {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,PATCH,OPTIONS");

		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		String plaintext = this.createToken();
		//encrypt
		MessageDigest m;
		try{
		m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(plaintext.getBytes());
		byte[] digest = m.digest();
		//Decoding
		BigInteger bigInt = new BigInteger(1,digest);
		String hashtext = bigInt.toString(16);
		while(hashtext.length() < 32 ){
			hashtext = "0"+hashtext;
		}
		//end decrypt
		if (dataAccess.login(un, pw, hashtext))
			request.setAttribute("name",plaintext);
		else 
			request.setAttribute("name", "denied");
		request.getRequestDispatcher("response.jsp").forward(request, response);
		return;
		}catch(Exception e){
			System.out.println(e);
		}
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
