package gr.gradle.demo.api;
import org.restlet.data.Header;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.Limits;
import gr.gradle.demo.data.model.Product;
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

public class ProductsResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
    
    @Override
    protected Representation get() throws ResourceException {
        int start,count;
        String str_count = getQueryValue("count");
        String str_start = getQueryValue("start");
        String sort = getQueryValue("sort");
        String status = getQueryValue("status");
        System.out.println(sort);
        if (str_count == null){
          count = 20;
        }
        else{
          count = Integer.parseInt(str_count);
        }
        if (str_start==null){
          start = 0;
        }
        else{
          start = Integer.parseInt(str_start);
        }

        List<Product> products = dataAccess.getProducts(new Limits(start, count),sort,status);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("count", count);
        if (products == null)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No products available");
        map.put("total", products.size());
        map.put("products", products);

        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
      Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
      String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
      int rights = dataAccess.isloggedin(token);
      if(rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
      //Create a new restlet form
      Form form = new Form(entity);
      //Read the parameters
      String name = form.getFirstValue("name");
      String description = form.getFirstValue("description");
      String category = form.getFirstValue("category");
      boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
      String tags = form.getFirstValue("tags");
      Map<String, Object> map = new HashMap<>();
      //validate the values (in the general case)
      if (name==null || category==null || name.equals("") || category.equals(""))
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
      
      Product product = dataAccess.addProduct(name, description, category, withdrawn, tags);
      map.put("new product",product);
      return new JsonMapRepresentation(map);
    }
    
    @Override
    protected Representation options(){
        Series responseHeaders;
        responseHeaders = new Series(Header.class);
        getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));


        return null;
    }
}
