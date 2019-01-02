package gr.gradle.demo.api;
import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Product;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

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
        //TODO: Implement this DONE//
        String idAttr = getAttribute("id");
        if (idAttr==null)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Missing product id");
        Long id = null;
        try{
          id = Long.parseLong(idAttr);
        }
        catch(Exception e){
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid product id: " + idAttr);
        }
        if (dataAccess.deleteProduct(id)==false)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid product id: " + idAttr);
        Map<String, Object> map = new HashMap<>();
        map.put("Message","OK");
        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {
        //Read the parameters
        //TODO: Implement this DONE//
        String idAttr = getAttribute("id");
        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        String desc = form.getFirstValue("description");
        String category = form.getFirstValue("category");
        boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
        String tags = form.getFirstValue("tags");
        Map<String, Object> map = new HashMap<>();
        System.out.println(name);
        if (idAttr==null || name==null || category==null){
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
        }
        Long id = null;
        try{
          id = Long.parseLong(idAttr);
        }
        catch(Exception e){
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
        }
        Optional<Product> opt =dataAccess.putProduct(id,name,desc,withdrawn,tags,category);
        Product product = opt.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + idAttr));
        map.put("Product",product);
       return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation patch(Representation entity) throws ResourceException {
        //Read the parameters
        //TODO: Implement this DONE//
        String idAttr = getAttribute("id");
        Form form = new Form(entity);
        String name = form.getFirstValue("name");
        String desc = form.getFirstValue("description");
        String category = form.getFirstValue("category");
        boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));
        String tags = form.getFirstValue("tags");
        Map<String, Object> map = new HashMap<>();
        System.out.println(name);
        if (idAttr==null){
            map.put("Message","Invalid Values");
            return new JsonMapRepresentation(map);
        }
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
