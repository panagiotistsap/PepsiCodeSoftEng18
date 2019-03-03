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
      List<Product> products;
      Map<String, Object> map = new HashMap<>();
      int start,count;
      String str_count = getQueryValue("count");
      String str_start = getQueryValue("start");
      String sort = getQueryValue("sort");
      String status = getQueryValue("status");
      try{
        if (str_count == null)
          count = 20;
        else
          count = Integer.parseInt(str_count);
        if (str_start==null)
          start = 0;
        else
          start = Integer.parseInt(str_start);
        products = dataAccess.getProducts(new Limits(start, count),sort,status);
      }catch(Exception e){
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
      }
      if (products==null)
        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "no products available"); 
      map.put("start", start);
      map.put("count", count);
        
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
          throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
        //Create a new restlet form
        Form form = new Form(entity);
        //Read the parameters
        String name = form.getFirstValue("name");
        String description = form.getFirstValue("description");
        String category = form.getFirstValue("category");
        String str_with  = form.getFirstValue("withdrawn");
        String tags = form.getFirstValue("tags");
        System.out.println(tags);
        Map<String, Object> map = new HashMap<>();
        if (name==null || category==null || name.equals("") || category.equals("")
          || description==null || description.equals("") || tags==null || tags.equals(""))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
        Boolean withdrawn;
        withdrawn = null;
        if (str_with!=null){
            if (!((str_with.equals("0") || str_with.equals("1") || str_with.equals("true") || str_with.equals("false"))))
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid withdrawn Values");
            withdrawn = str_with.equals("1") || str_with.equals("true");
        }
        else
          withdrawn = false;
        //validate the values (in the general case)
        try{
        Product product = dataAccess.addProduct(name, description, category, withdrawn, tags);
        //map.put("new product",product);
        return new JsonProductRepresentation(product);
      }catch(Exception e){
        System.out.println("Error");
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
      }
    }

    
    @Override
    protected Representation options(){
        Series responseHeaders;
        responseHeaders = new Series(Header.class);
        getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        responseHeaders.add(new Header("Access-Control-Allow-Methods", "POST,GET,OPTIONS"));


        return null;
    }

}
