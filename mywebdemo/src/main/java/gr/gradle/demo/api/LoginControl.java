package gr.gradle.demo.api;
import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Product;
import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.engine.header.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;
import java.util.*;
import org.restlet.util.*;
import java.lang.Object;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.restlet.data.Header;

import java.security.MessageDigest;
import java.math.BigInteger;
/**
 * Servlet implementation class LoginController
 */

public class LoginControl extends ServerResource {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		Form form = new Form(entity);
		String un = form.getFirstValue("username");
		String pw = form.getFirstValue("password");
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
		Map<String, String> map = new HashMap<>();
		//end decrypt
		if (dataAccess.login(un, pw, hashtext))
			map.put("token",plaintext);
		else 
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
		System.out.println("gamw");
		return new JsonMapRepresentation(map);
		}catch(Exception e){
			System.out.println(e);
			return null;
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
