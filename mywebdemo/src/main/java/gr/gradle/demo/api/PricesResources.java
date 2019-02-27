package gr.gradle.demo.api;
import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Product;
import gr.gradle.demo.data.model.Price;
import gr.gradle.demo.data.model.Result;
import gr.gradle.demo.data.Limits;
import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PricesResources extends ServerResource {

	private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		String token = getQueryValue("token");
      	int rights = dataAccess.isloggedin(token);
      	if(rights==-1)
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid Request");	
		try{
	    Form form = new Form(entity);
        String productid = form.getFirstValue("productid");
        String shopid = form.getFirstValue("shopid");
        String date_from = form.getFirstValue("dateFrom");
        String date_to = form.getFirstValue("dateTo");
        String price = form.getFirstValue("price");
      	if(!date_to.matches("\\d{4}-\\d{2}-\\d{2}") || !date_from.matches("\\d{4}-\\d{2}-\\d{2}")){
      		throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid date values");
		}
		System.out.println("ftanw");
		Double d_price = Double.parseDouble(price);
		Long l_shopid = Long.parseLong(shopid);
		Long l_productid = Long.parseLong(productid);
		System.out.println("ftanw2");
		Price final_price = dataAccess.postPrice(l_productid,l_shopid,d_price,date_from,date_to);
    return new JsonPriceRepresentation(final_price);
		}catch(Exception e){
			System.out.println("ERROR");
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid values");
		}
	}

	@Override
    protected Representation get() throws ResourceException {

		

		try{
			int start,count,i,j;
			String str_count = getQueryValue("count");
			String str_start = getQueryValue("start");
			String status = getQueryValue("status");
			String str_geodist = getQueryValue("geoDist");
			String str_Lng = getQueryValue("geoLng");
			String str_Lat = getQueryValue("geoLat");
			String str_dateFrom = getQueryValue("dateFrom");
			String str_dateTo = getQueryValue("dateTo");
			String str_shops = getQueryValue("shops");
			String str_products = getQueryValue("products");
			String str_tags = getQueryValue("tags");
			String sort = getQueryValue("sort");
			
			//count check
			if (str_count == null)
				count = 20;
			else
				count = Integer.parseInt(str_count);
			//start check
			if (str_start==null)
				start = 0;
			else
				start = Integer.parseInt(str_start);
			//sort check
			String[] sort_list;
			if (sort==null){
				System.out.println("dick");
				sort_list = new String[1];
				sort_list[0] = "price|ASC";
			}
			else{
				String[] parts = sort.split(",");
				sort_list = new String[parts.length];
				System.out.println(parts.length);
				for (i=0;i<parts.length;i++){
					sort = parts[i];
					if (!(sort!="geo.dist|ASC" && sort!="geo.dist|DESC" && sort!="price|ASC" && sort!="price|DESC" 
						&& sort!="date|ASC" && sort!="date|DESC"))
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Wrong sort value");
					System.out.println("ftasame");
					sort_list[i]=sort;
				}
			}
			System.out.println("ftanw1");
			//check geo infos
			int geoDist; Double Lng=null,Lat=null;
			geoDist=0;
			
			System.out.println(str_Lng);
			if (!((str_Lng==null && str_Lat==null && str_geodist==null) ||(str_Lng!=null && str_Lat!=null && str_geodist!=null)))
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Need all of geo infos or none");
			else if(str_Lng!=null){
				
				geoDist = Integer.parseInt(str_geodist);
				Lng = Double.parseDouble(str_Lng);
				Lat = Double.parseDouble(str_Lat);
			}
			System.out.println("ftanw2");
			//check dates
			if (!((str_dateFrom==null && str_dateTo==null) || (str_dateFrom!=null || str_dateTo!=null)))
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Need all of date infos or none");
			else if(str_dateFrom!=null){
				if(!str_dateFrom.matches("\\d{4}-\\d{2}-\\d{2}") || !str_dateTo.matches("\\d{4}-\\d{2}-\\d{2}"))
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid date values");
			}
			System.out.println("ftanw3");
			//check shops
			Long[] shopsids=null;
			if (str_shops!=null){
				String[] parts = str_shops.split(",");
				shopsids = new Long[parts.length];
				for(i=0;i<parts.length;i++);{
					shopsids[i] = Long.parseLong(parts[i]);
				}
			}
			System.out.println("ftanw4");
			//check shops
			Long[] productids = null;
			if (str_products!=null){
				String[] parts = str_products.split(",");
				productids = new Long[parts.length];
				for(i=0;i<parts.length;i++);{
					productids[i] = Long.parseLong(parts[i]);
				}
			}
			System.out.println("ftanw5");
			//check tags
			String[] tags = null;
			if (str_tags!=null){
				String[] parts = str_tags.split(",");
				tags = new String[parts.length];
				for(i=0;i<parts.length;i++);{
					tags[i] = parts[i];
				}
			}
			//bazw ta tags se hashmap
			HashMap<String,Integer> tags_map = new HashMap<String,Integer>();
			for(i=0;i<tags.length;i++){
				tags_map.put(tags[i],1);
			}
			System.out.println("ftanw6");
			List<Result> results = dataAccess.getResults(new Limits(start, count),sort_list,geoDist,Lng,Lat,shopsids,productids,tags,str_dateFrom,str_dateTo);
			Map<String, Object> map = new HashMap<>();
			//elegxos gia tags
			List<Result> results_aftertags = new ArrayList<Result>(); Result curr; String[] tags_array;
			for(i=0;i<results.size();i++){
				curr = results.get(i);
				tags_array = curr.gettags().split(",");
				for(j=0;j<tags_array.length;j++){
					if(tags_map.containsKey(tags_array[j])){
						results_aftertags.add(curr);
						break;
					}
				}
			}
			results = results_aftertags;

			HashMap<Double, List<Result>> price_map = new HashMap<>();
			Double price_help; List<Result> help_list;
			for(i = 0;i <results.size();i++){
				price_help = results.get(i).getprice();
				if (price_map.containsKey(price_help))
					help_list = price_map.get(price_help);
				else{
					help_list = new ArrayList<Result>();
					price_map.put(price_help,help_list);
				}
				help_list.add(results.get(i));
			}
			map.put("start", start);
			map.put("count", count);
			map.put("total", price_map.size());
			map.put("prices",price_map);
			//return null;
			
			return new JsonMapRepresentation(map);
		}catch(Exception e){
			System.out.println(e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid values eksoterika");

		}
	}


}