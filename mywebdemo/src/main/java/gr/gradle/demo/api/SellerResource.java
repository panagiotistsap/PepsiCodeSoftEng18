package gr.gradle.demo.api;

import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Seller;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.Optional;

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
        //TODO: Implement this
        throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
    }
}
