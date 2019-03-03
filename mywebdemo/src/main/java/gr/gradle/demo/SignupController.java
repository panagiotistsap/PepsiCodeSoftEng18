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

public class SignupController extends HttpServlet {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,PATCH,OPTIONS");

		String un = request.getParameter("username");
        String plaintext = request.getParameter("password");
        String pw2 = request.getParameter("password2");
        String SS="";
        Boolean res=true;
        if (un==null || plaintext==null || pw2==null){
			res = false; SS = "Συμπλήρωσε όλες τις τιμές";
			request.setAttribute("name",SS);
            request.getRequestDispatcher("response.jsp").forward(request, response);
        }
        if (res && !plaintext.equals(pw2)){
			res = false; SS = "Δεν ταιριάζουν οι κωδικοί";
			request.setAttribute("name",SS);
            request.getRequestDispatcher("response.jsp").forward(request, response);
        }
        if (res && !dataAccess.ch_un_avl(un)){
			res = false; SS = "Το όνομα χρησιμοποιείται ήδη";
			request.setAttribute("name",SS);
            request.getRequestDispatcher("response.jsp").forward(request, response);
        }
        if (!res){
            request.setAttribute("name",SS);
            request.getRequestDispatcher("response.jsp").forward(request, response);
        }
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
		System.out.println("kai edw");
		if (dataAccess.signup(un, hashtext))
			request.setAttribute("name","Success");
		else 
			request.setAttribute("name","Sign up Failed" );
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
