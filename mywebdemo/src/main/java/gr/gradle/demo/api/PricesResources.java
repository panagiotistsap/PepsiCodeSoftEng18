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
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PricesResources extends ServerResource {

	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		String token = getQueryValue("token");
      	int rights = dataAccess.isloggedin(token);
      	if(rights==-1)
        	throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");	
        Form form = new Form(entity);
        String productid = form.getFirstValue("productid");
        String shopid = form.getFirstValue("shopid");
        String date_from = form.getFirstValue("dateFrom");
        String date_to = form.getFirstValue("dateTo");
        String price = form.getFirstValue("price");
      	if(!date_to.matches("\\d{4}-\\d{2}-\\d{2}") || !date_from.matches("\\d{4}-\\d{2}-\\d{2}")){
      		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid date values");
      	}
		Double d_price = Double.parseDouble(price);
		Long l_shopid = Long.parseLong(shopid);
		Long l_productid = Long.parseLong(productid);
		 
		return null;
	}

}