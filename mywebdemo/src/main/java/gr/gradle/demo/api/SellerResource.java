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
import java.util.*;
import org.restlet.util.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.restlet.data.Header;
public class SellerResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation options(){
        Series responseHeaders;
        responseHeaders = new Series(Header.class);
        getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        responseHeaders.add(new Header("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PATCH,DELETE"));
        return null;
    }

    @Override
    protected Representation get() throws ResourceException {
        String idAttr = getAttribute("id");
        System.out.println(idAttr);
        if (idAttr == null) 
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing seller id");
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
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
        int rights = dataAccess.isloggedin(token);
        if (rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
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
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "no shop with id: " + idAttr); //elegxos gia to an to esvise h bash
        Map<String, Object> map = new HashMap<>();
        map.put("Message","OK");
        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException{
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
        int rights = dataAccess.isloggedin(token);
        if(rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
        
        String idAttr = getAttribute("id");
        if (idAttr==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No id value");    
        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        String address = form.getFirstValue("address");
        String  str_Lng = form.getFirstValue("lng");
        String str_Lat = form.getFirstValue("lat");
        String tags = form.getFirstValue("tags");
        String str_with = form.getFirstValue("withdrawn");
        Map<String, Object> map = new HashMap<>();    
        Boolean withdrawn;
        //Read the parameters
        //TODOne: Implement this DONE//
        if (name==null || name.equals("") || address==null || address.equals("") 
              || str_Lng==null || str_Lng.equals("") || str_Lat==null || str_Lat.equals("") ||
              str_with==null || str_with.equals("") || tags==null || tags.equals("") )
              throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Fill all the values"); 
        Long id = null;
        try{
            //tsekarei an to id einai int
            id = Long.parseLong(idAttr);
        }
        catch(Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid id value");
        }
        //check withdrawn
        if (!((str_with.equals("0") || str_with.equals("1") || str_with.equals("true") || str_with.equals("false"))))
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid withdrawn Values");
          withdrawn = str_with.equals("1") || str_with.equals("true");
        //check Lng,Lat
        Double lng,lat;
        try{
            lng = Double.valueOf(str_Lng);
            lat = Double.valueOf(str_Lat);
        }catch(Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid lat,lng Values");
        }
        if (!(lng>-180 && lng<180 && lat>-90 && lat<90))
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid lat,lng Values");

        Optional<Seller> opt = dataAccess.putShop(id,name,address,lng,lat,tags,withdrawn);
        Seller shop = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Shop not found - id: " + idAttr));
        map.put("Parking Lots",shop);
        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException{
        ArrayList<String> inp = new ArrayList<String>();
        Series headers = (Series) getRequestAttributes().get("org.restlet.http.headers");
        String token = headers.getFirstValue("X-OBSERVATORY-AUTH");
        int rights = dataAccess.isloggedin(token);
        if(rights==-1)
            throw new ResourceException(Status.CLIENT_ERROR_FORBIDDEN , "You dont have access here");
            
        //Read the parameters
        //TODOne: Implement this DONE//
        String idAttr = getAttribute("id");
        if (idAttr==null)
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No id value");    
        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        
        String address = form.getFirstValue("address");
        String str_lng = form.getFirstValue("lng");
        String str_lat = form.getFirstValue("lat");
        String str_with = form.getFirstValue("withdrawn");
        String tags = form.getFirstValue("tags");
        inp.add(address); inp.add(str_lng); inp.add(str_lat); inp.add(str_lng); inp.add(str_with);
        inp.add(tags);
        if (this.countnonulls(inp)<=0)
        throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid values"); 
        Double lng,lat; 
        Boolean withdrawn;
        //tsekarei an to id einai int
        Long id = null;
        try{
            id = Long.parseLong(idAttr);
        }catch(Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Id value"); 
        }
        if ((name!=null  && name.equals("")) || (address!=null && address.equals("")) 
        || (str_lng!=null && str_lng.equals("")) || (str_lat!=null && str_lat.equals("")) ||
        (str_with!=null && str_with.equals("")) || (tags!=null && tags.equals("")))
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No empty values"); 
        //check gia lng,lat
        lng = null; lat = null;
        try{
            if (str_lng!=null)
                lng = Double.valueOf(str_lng);
            if (str_lat!=null)
                lat = Double.valueOf(str_lat);
        }
        catch(Exception e){
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid lat,lng values"); 
        }
        System.out.println(lng);
        System.out.println(lat);
        if (lng!=null && (lng<-90 || lng>90))
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid lng value");
        if (lat!=null && (lat<-90 || lat>90))
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid lat value");
        //check gia withdrawn
        withdrawn = null;
        if (str_with!=null){
            if (!((str_with.equals("0") || str_with.equals("1") || str_with.equals("true") || str_with.equals("false"))))
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid withdrawn Values");
            withdrawn = str_with.equals("1") || str_with.equals("true");
        }
        Map<String, Object> map = new HashMap<>();
        Optional<Seller> opt = dataAccess.patchShop(id,name,address,lng,lat,tags,withdrawn);
        Seller shop = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Shop not found - id: " + idAttr));
        map.put("Parking Lots",shop);
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
