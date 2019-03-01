package gr.gradle.demo.api;
import org.restlet.data.Header;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.Limits;
import gr.gradle.demo.data.model.Seller;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import java.util.*;
import org.restlet.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellersResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation options(){
        Series responseHeaders;
        responseHeaders = (Series<Header>) getResponse().getAttributes().get("orgorg.restlet.http.headers");
        if (responseHeaders==null){
          responseHeaders = new Series(Header.class);
          getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
          responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        }

        return null;
    }
    
    @Override
    protected Representation get() throws ResourceException {
        int start,count;

        String str_count = getQueryValue("count");
        String str_start = getQueryValue("start");
        String sort = getQueryValue("sort");
        String status = getQueryValue("status");
        System.out.println(sort);
        if (str_count == null)
          count = 20;
        else
          count = Integer.parseInt(str_count);
        if (str_start==null)
          start = 0;
        else
          start = Integer.parseInt(str_start);
        List<Seller> sellers = dataAccess.getSellers(new Limits(start, count),sort,status);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("count", count);
        if (sellers==null)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No sellers available");
        else
          map.put("total", sellers.size());
        map.put("parking_lots", sellers);

        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
      Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
      String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
      int rights = dataAccess.isloggedin(token);
      if (rights==-1)
      throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
      Form form = new Form(entity);
      Map<String, Object> map = new HashMap<>();
      try{
        String name = form.getFirstValue("name");
        String address = form.getFirstValue("address");
        Double Ing = Double.valueOf(form.getFirstValue("Ing"));
        Double Iat = Double.valueOf(form.getFirstValue("Iat"));
        String tags = form.getFirstValue("tags");
        String string_withdrawn = form.getFirstValue("withdrawn");
        if (name==null || name.equals("") || address==null || address.equals("") 
              || Ing==null || Iat==null || (!string_withdrawn.equals("0") && !string_withdrawn.equals("1"))){
          map.put("Message","Invalid Values");
          return new JsonMapRepresentation(map);
        }
        Boolean withdrawn = true;//Boolean.valueOf(string_withdrawn);
        Seller seller = dataAccess.addSeller(name, address, Ing, Iat, tags, withdrawn);
        return new JsonSellerRepresentation(seller);
      }
      catch(Exception e){
        map.put("Message","Invalid Values");
        return new JsonMapRepresentation(map);
      }
    }
    
}
