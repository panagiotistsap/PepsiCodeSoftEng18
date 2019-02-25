package gr.gradle.demo.api;
import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Seller;
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
public class SellerResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {

        String idAttr = getAttribute("id");
        System.out.println(idAttr);

        if (idAttr == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing seller id");
        }

        Long id = null;
        try {
            id = Long.parseLong(idAttr);
        }
        catch(Exception e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid seller id: " + idAttr);
        }

        Optional<Seller> optional = dataAccess.getSeller(id);
        Seller seller = optional.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Seller not found - id: " + idAttr));

        return new JsonSellerRepresentation(seller);
    }

    @Override
    protected Representation delete() throws ResourceException {
        String token = getQueryValue("token");
        int rights = dataAccess.isloggedin(token);
        if (rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
        String idAttr = getAttribute("id");
        if (idAttr==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing shop id");
        Long id = null;
        try{
            id = Long.parseLong(idAttr);
        }catch(Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid shop id: " + idAttr);
        }
        if (dataAccess.deleteShop(id,rights)==false)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid shop id: " + idAttr); //elegxos gia to an to esvise h bash
        Map<String, Object> map = new HashMap<>();
        map.put("Message","OK");
        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException{
        String token = getQueryValue("token");
        int rights = dataAccess.isloggedin(token);
        if(rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
            
        //Read the parameters
        //TODOne: Implement this DONE//
        try{
            String idAttr = getAttribute("id");
            Form form = new Form(entity);
            String name = form.getFirstValue("name");
            String address = form.getFirstValue("address");
            Double Ing = Double.valueOf(form.getFirstValue("Ing"));
            Double Iat = Double.valueOf(form.getFirstValue("Iat"));
            String tags = form.getFirstValue("tags");
            Boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
            Map<String, Object> map = new HashMap<>();
            //blepei an yparxoun valid stoixeia
            if (name==null || name.equals("") || address==null || address.equals("") 
              || Ing==null || Iat==null ) {
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
            }
            //tsekarei an to id einai int
            Long id = null;
            id = Long.parseLong(idAttr);
            Optional<Seller> opt = dataAccess.putShop(id,name,address,Ing,Iat,tags,withdrawn);
            Seller shop = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Shop not found - id: " + idAttr));
            map.put("Parking Lots",shop);
            return new JsonMapRepresentation(map);
        }
        catch(Exception e){
            Map<String, Object> map = new HashMap<>();
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
        }
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException{
        String token = getQueryValue("token");
        int rights = dataAccess.isloggedin(token);
        if(rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");
            
        //Read the parameters
        //TODOne: Implement this DONE//
        try{
            String idAttr = getAttribute("id");
            Form form = new Form(entity);
            String name = form.getFirstValue("name");
            String address = form.getFirstValue("address");
            String str_ing = form.getFirstValue("Ing");
            String str_iat = form.getFirstValue("Iat");
            String str_with = form.getFirstValue("withdrawn");
            String tags = form.getFirstValue("tags");
            Double Ing,Iat; 
            Boolean withdrawn;
            Ing = null;
            Iat = null;
            withdrawn = null;
            if (str_ing!=null)
                Ing = Double.valueOf(form.getFirstValue("Ing"));
            if (str_iat!=null)
                Iat = Double.valueOf(form.getFirstValue("Iat"));
            if (str_with!=null)
                withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));

            Map<String, Object> map = new HashMap<>();
            //blepei an yparxoun valid stoixeia
            if (name.equals("") || address.equals("")) {
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
            }
            //tsekarei an to id einai int
            Long id = null;
            id = Long.parseLong(idAttr);
            Optional<Seller> opt = dataAccess.patchShop(id,name,address,Ing,Iat,tags,withdrawn);
            Seller shop = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Shop not found - id: " + idAttr));
            map.put("Parking Lots",shop);
            return new JsonMapRepresentation(map);
        }
        catch(Exception e){
            Map<String, Object> map = new HashMap<>();
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
        }
    }
    
}
