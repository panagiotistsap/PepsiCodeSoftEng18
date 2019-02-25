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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductResource extends ServerResource {

    

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        String idAttr = getAttribute("id");
        System.out.println(idAttr);

        if (idAttr == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing product id");
        }

        Long id = null;
        try {
            id = Long.parseLong(idAttr);
        }
        catch(Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid product id: " + idAttr);
        }

        Optional<Product> optional = dataAccess.getProduct(id);
        Product product = optional.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));

        return new JsonProductRepresentation(product);
    }

    @Override
    protected Representation delete() throws ResourceException {
      String token = getQueryValue("token");
      int rights = dataAccess.isloggedin(token);
      if (rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
      String idAttr = getAttribute("id");
      //elegxos an exei dosei id
      if (idAttr==null)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing product id");
      Long id = null;
      //elegxos an einai arithmos 
      try{
        id = Long.parseLong(idAttr);
      }
      catch(Exception e){
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid product id: " + idAttr);
      }

      if (dataAccess.deleteProduct(id,rights)==false)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid product id: " + idAttr); //elegxos gia to an to esvise h bash
      Map<String, Object> map = new HashMap<>();
      map.put("Message","OK");
      return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {
      String token = getQueryValue("token");
      int rights = dataAccess.isloggedin(token);
      if(rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
        
      //Read the parameters
     
      String idAttr = getAttribute("id");
      Form form = new Form(entity);
      String name = form.getFirstValue("name");
      String desc = form.getFirstValue("description");
      String category = form.getFirstValue("category");
      boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
      String tags = form.getFirstValue("tags");
      Map<String, Object> map = new HashMap<>();
      System.out.println(name);
      //blepei an yparxoun valid stoixeia
      if (idAttr==null || name==null || category==null || name.equals("") || category.equals("")){
        map.put("Message","Invalid Values");
        return new JsonMapRepresentation(map);
      }
      //tsekarei an to id einai int
      Long id = null;
     try{
        id = Long.parseLong(idAttr);
      }
      catch(Exception e){
        map.put("Message","Invalid Values");
        return new JsonMapRepresentation(map);
      }
      Optional<Product> opt = dataAccess.putProduct(id,name,desc,withdrawn,tags,category);
      Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
      map.put("Product",product);
      return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException {
      //check if logged in
      String token = getQueryValue("token");
      int rights = dataAccess.isloggedin(token);
      if(rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
      //Read the parameters
      String idAttr = getAttribute("id");
      Form form = new Form(entity);
      String name = form.getFirstValue("name");
      String desc = form.getFirstValue("description");
      String category = form.getFirstValue("category");
      boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
      String tags = form.getFirstValue("tags");
      Map<String, Object> map = new HashMap<>();
      //check if values are valid
      if (idAttr==null || (name !=null && name.equals("")) || (category!=null && category.equals(""))){
          map.put("Message","Invalid Values");
          return new JsonMapRepresentation(map);
      }
      //check if id is integer
      Long id = null;
      try{
        id = Long.parseLong(idAttr);
      }
      catch(Exception e){
          map.put("Message","Invalid Values");
          return new JsonMapRepresentation(map);
      }
      Optional<Product> opt =dataAccess.patchProduct(id,name,desc,withdrawn,tags,category);
      Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
      map.put("Product",product);
      return new JsonMapRepresentation(map);
    }

}
