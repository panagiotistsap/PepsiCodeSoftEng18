package gr.gradle.demo.api;

import com.google.gson.Gson;
import gr.gradle.demo.data.model.Seller;
import org.restlet.data.MediaType;
import org.restlet.representation.WriterRepresentation;

import java.io.IOException;
import java.io.Writer;

public class JsonSellerRepresentation extends WriterRepresentation {

    private final Seller seller;

    public JsonSellerRepresentation(Seller seller) {
        super(MediaType.APPLICATION_JSON);
        this.seller = seller;
    }

    @Override
    public void write(Writer writer) throws IOException {
        Gson gson = new Gson();
        writer.write(gson.toJson(seller));
    }
}
