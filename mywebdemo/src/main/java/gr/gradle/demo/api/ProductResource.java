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
      Optional<Product> opt;
      Map<String, Object> map = new HashMap<>();
      String idAttr = getAttribute("id");
      //Read the parameters
      Form form = new Form(entity);
      String name = form.getFirstValue("name");
      String desc = form.getFirstValue("description");
      String category = form.getFirstValue("category");
      String str_with = form.getFirstValue("withdrawn");
      String tags = form.getFirstValue("tags");
      System.out.println(name);
      boolean withdrawn;
      Long id = null;
      if (str_with==null)
        withdrawn = false;
      else{
        if (!((str_with.equals("0") || str_with.equals("1") || str_with.equals("true") || str_with.equals("false"))))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid withdrawn Values");
          withdrawn = str_with.equals("1") || str_with.equals("true");
      }
        System.out.println("hello");
        //blepei an yparxoun valid stoixeia
        if (name==null || category==null || name.equals("") || category.equals("")
        || desc==null || desc.equals("") || tags==null || tags.equals(""))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
        
        //tsekarei an to id einai int
      try{
        id = Long.parseLong(idAttr);
        
      }catch(Exception e){
        //System.out.println(e);
        System.out.println("ERROR");
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid id value");
      }
      opt = dataAccess.putProduct(id,name,desc,withdrawn,tags,category);
      Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
      map.put("Product",product);
      return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException {
      //check if logged in
      ArrayList<String> inp = new ArrayList<String>();
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
      String str_with = form.getFirstValue("withdrawn");
      String tags = form.getFirstValue("tags");
      inp.add(name); inp.add(desc); inp.add(category); inp.add(str_with); inp.add(str_with); inp.add(tags);
      if (this.countnonulls(inp)<=0)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid values");
      Map<String, Object> map = new HashMap<>();
      Optional<Product> opt;
      Boolean withdrawn;
      Long id = null;
      try{
        id = Long.parseLong(idAttr);
      }catch(Exception e){
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid id value");
      }
      //check if values are valid
      if (idAttr==null || (name !=null && name.equals("")) || (category!=null && category.equals("")) || (desc!=null || desc.equals(""))
        || (tags==null || tags.equals(""))) 
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Values");
      
      if (str_with==null)
        withdrawn=null;
      else{
        if (!((str_with.equals("0") || str_with.equals("1") || str_with.equals("true") || str_with.equals("false"))))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid withdrawn Values");
        withdrawn = str_with.equals("1") || str_with.equals("true");
      }
      //check if id is integer
      opt = dataAccess.patchProduct(id,name,desc,withdrawn,tags,category);
      Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
      map.put("Product",product);
      return new JsonMapRepresentation(map);
    }

    public Integer countnonulls(ArrayList<String> inp){
      int sum=0;
      for(int i=0;i<inp.size();i++){
          if (inp.get(i)!=null)
              sum++;
      }
      if (sum==0)
          return -1;
      if (sum==1)
          return 1;
      return 0;
  }
}
