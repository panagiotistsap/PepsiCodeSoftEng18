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

public class ProductResource extends ServerResource {

    

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
      Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
      String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
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
      Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
      String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
     

      int rights = dataAccess.isloggedin(token);
      if (rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
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
        throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr); //elegxos gia to an to esvise h bash
      Map<String, Object> map = new HashMap<>();
      map.put("Message","OK");
      return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {
      System.out.println("wtf");
      Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
      String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
      int rights = dataAccess.isloggedin(token);
      if(rights==-1)
        throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
        
      //Read the parameters
      try{
        String idAttr = getAttribute("id");
        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        String desc = form.getFirstValue("description");
        String category = form.getFirstValue("category");
        String str_with = form.getFirstValue("withdrawn");
        String tags = form.getFirstValue("tags");
        Map<String, Object> map = new HashMap<>();
        System.out.println(name);
        boolean withdrawn;
        if (str_with==null)
          withdrawn = false;
        else
          withdrawn = Boolean.valueOf(str_with);
        System.out.println("hello");
        //blepei an yparxoun valid stoixeia
        if (idAttr==null || name==null || category==null || name.equals("") || category.equals(""))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values madafaka");
        
        //tsekarei an to id einai int
        Long id = null;
        id = Long.parseLong(idAttr);
        System.out.println("gamiesai");
        Optional<Product> opt = dataAccess.putProduct(id,name,desc,withdrawn,tags,category);
        Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
        map.put("Product",product);
        return new JsonMapRepresentation(map);
      }catch(Exception e){
        System.out.println(e);
        System.out.println("ERROR");
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
      }
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException {
      //check if logged in
      try{
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
        int rights = dataAccess.isloggedin(token);
        if(rights==-1)
          throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
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
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
        }
        //check if id is integer
        Long id = null;
        id = Long.parseLong(idAttr);
        Optional<Product> opt =dataAccess.patchProduct(id,name,desc,withdrawn,tags,category);
        Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
        map.put("Product",product);
        return new JsonMapRepresentation(map);
      }catch(Exception e){
        System.out.println(e);
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");

      }
    }

}
