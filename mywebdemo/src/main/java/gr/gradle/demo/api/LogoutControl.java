package gr.gradle.demo.api;
import java.util.Random;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.conf.Configuration;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Product;
import gr.gradle.demo.data.model.Price;
import gr.gradle.demo.data.model.Result;
import gr.gradle.demo.data.Limits;
import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import org.restlet.util.*;

/**
 * Servlet implementation class LoginController
 */

public class LogoutControl extends ServerResource {
	
	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		System.out.println("Mpika");
		
		Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
		String token = headers.getFirstValue("X-OBSERVATORY-AUTH");//headers.getFirstValue("X-OBSERVATORY-AUTH");
		  //System.out.println(token);
		Map<String, String> map = new HashMap<>();	
		if (dataAccess.logout(token))
			map.put("message", "ok");
		else 
			map.put("message", "error");
		System.out.println("Douleuei kala");
		return new JsonMapRepresentation(map);
		
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
