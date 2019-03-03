package gr.gradle.demo.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestfulApp extends Application {

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        //GET, POST
        router.attach("/products", ProductsResource.class);

        //GET, DELETE
        router.attach("/products/{id}", ProductResource.class);

        router.attach("/shops" , SellersResource.class);

        router.attach("/shops/{id}" , SellerResource.class);

        router.attach("/prices" , PricesResources.class);

        router.attach("/login", LoginControl.class);
         
        //router.attach("/logout", LogoutControl.class);

        return router;
    }

}
