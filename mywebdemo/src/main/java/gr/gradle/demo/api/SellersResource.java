package gr.gradle.demo.api;

import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.Limits;
import gr.gradle.demo.data.model.Seller;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellersResource extends ServerResource {

    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

    @Override
    protected Representation get() throws ResourceException {
        int start,count;
        Long total;

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

        List<Seller> sellers = dataAccess.getSellers(new Limits(start, count),sort,status);
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("count", count);
        if (sellers==null)
          throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "No sellers available");
        else
          map.put("total", sellers.size());
        map.put("sellers", sellers);

        return new JsonMapRepresentation(map);
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {

        //Create a new restlet form
        Form form = new Form(entity);
        //Read the parameters
        String name = form.getFirstValue("name");
        String address = form.getFirstValue("address");
        Double Ing = Double.valueOf(form.getFirstValue("Ing"));
        Double Iat = Double.valueOf(form.getFirstValue("Iat"));
        String tags = form.getFirstValue("tags");
        boolean withdrawn = Boolean.valueOf(form.getFirstValue("withdrawn"));



        //validate the values (in the general case)
        //...

        Seller seller = dataAccess.addSeller(name, address, Ing, Iat, tags, withdrawn);

        return new JsonSellerRepresentation(seller);
    }
}
