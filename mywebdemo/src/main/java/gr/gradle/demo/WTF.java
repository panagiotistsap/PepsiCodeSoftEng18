import org.restlet.data.Form;
import gr.gradle.demo.conf.Configuration;
import gr.gradle.demo.data.DataAccess;
import gr.gradle.demo.data.model.Product;
import gr.gradle.demo.data.model.Price;
import gr.gradle.demo.data.model.Result;
import gr.gradle.demo.data.Limits;

import com.google.gson.Gson;
import gr.gradle.demo.data.model.Product;
import org.restlet.data.MediaType;
import org.restlet.representation.WriterRepresentation;

import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import java.text.ParseException;
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
import java.util.*;
import org.restlet.util.*;
import org.restlet.data.Header;
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

public class WTF extends HttpServlet{
    
    private final DataAccess dataAccess = Configuration.getInstance().getDataAccess();
    
    public String get(String str_dateFrom,String str_dateTo,String str_products,String str_shops) throws ResourceException {
		int start,count,i,j;
		String str_count = null;
		String str_start = null;
		String status = null;
		String str_geodist = null;
		String str_Lng = null;
        String str_Lat = null;
        String sort = null;
        String str_tags = null;
        String inner_sort="price|ASC";
		try{
			//count check
			if (str_count == null)
				count = 20;
			else
				count = Integer.parseInt(str_count);
		}catch(Exception e){
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Wrong count value");
		}
		try{
			//start check
			if (str_start==null)
				start = 0;
			else
				start = Integer.parseInt(str_start);
		}catch(Exception e){
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Wrong start value");
		}
			//sort check
			String[] sort_list;
			if (sort==null){
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
					if ((sort.equals("geo.dist|ASC") || sort.equals("geo.dist|DESC"))
					 && (str_Lng==null || str_Lat==null || str_geodist==null))
					
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Wrong sort value");
					if (sort.equals("price|DESC"))
						inner_sort=sort;
					System.out.println("ftasame");
					sort_list[i]=sort;
				}
			}
			System.out.println("ftanw1");
			//check geo infos
			int geoDist; Double Lng=null,Lat=null;
			geoDist=0;
			
			System.out.println(str_Lng);
			if (!((str_Lng==null && str_Lat==null && str_geodist==null) || (str_Lng!=null && str_Lat!=null && str_geodist!=null)))
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Need all of geo infos or none");
			else if(str_Lng!=null){
				try{
					System.out.println("gamwwwwww");
					geoDist = Integer.parseInt(str_geodist);
					Lng = Double.parseDouble(str_Lng);
					Lat = Double.parseDouble(str_Lat);
				}catch(Exception e){
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Wrong geo values");
				}
			}
			else	
				System.out.println("hellooo");
			System.out.println("ftanw2");
			//check dates
			Date date1=null;
			Date date2=null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			format.setLenient(false);
			if (!((str_dateFrom==null && str_dateTo==null) || (str_dateFrom!=null && str_dateTo!=null)))
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Need all of date infos or none");
			else if(str_dateFrom!=null){
				if(!isValidDate(str_dateFrom) || !isValidDate(str_dateTo))
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid date values");
				try{
					date1=format.parse(str_dateFrom);
					date2=format.parse(str_dateTo);
					if (date2.before(date1))
						throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Date from < Date to");
				}catch(Exception e){
					throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid dates");
				}
			}
			else{
				Calendar calendar = Calendar.getInstance();
				str_dateFrom = format.format(calendar.getTime());
				str_dateTo   = format.format(calendar.getTime());
			}
			System.out.println("ftanw3");
			//check shops
			Long[] shopsids=null;
			if (str_shops!=null){
				String[] parts = str_shops.split(",");
				shopsids = new Long[parts.length];
				for(i=0;i<parts.length;i++){
					shopsids[i] = Long.parseLong(parts[i]);
				}
			}
			System.out.println("ftanw4");
			//check products
			Long[] productids = null;
			if (str_products!=null){
				String[] parts = str_products.split(",");
				productids = new Long[parts.length];
				for(i=0;i<parts.length;i++){
					productids[i] = Long.parseLong(parts[i]);
				}
			}
			System.out.println("ftanw5");
			//check tags
			String[] tags = null;
			if (str_tags!=null){
				String[] parts = str_tags.split(",");
				tags = new String[parts.length];
				for(i=0;i<parts.length;i++){
					tags[i] = parts[i];
				}
			}
			//bazw ta tags se hashmap
			HashMap<String,Integer> tags_map = new HashMap<String,Integer>();
			if (tags!=null){
				for(i=0;i<tags.length;i++){
					tags_map.put(tags[i],1);
				}
			}
			System.out.println("ftanw6");
			System.out.println(geoDist);
			List<Result> results = dataAccess.getResults(new Limits(start, count),sort_list,geoDist,Lng,Lat,shopsids,productids,tags,str_dateFrom,str_dateTo);
			Map<String, Object> map = new HashMap<>();
			//elegxos gia tags
			List<Result> results_aftertags = new ArrayList<Result>(); Result curr; String[] tags_array;
			if (tags!=null){
				System.out.println("brhka tags");
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
			}
			else
				results_aftertags = results;
			results = results_aftertags;
			if (results.size()==0){
				throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Not found");
			}
		try{
			HashMap<Double, List<Result>> price_map = new HashMap<>();
			Double price_help; List<Result> help_list;
			ArrayList<Double> arlist_prices = new ArrayList<Double>();
			for(i = 0;i <results.size();i++){
				price_help = results.get(i).getprice();
				if (price_map.containsKey(price_help))
					help_list = price_map.get(price_help);
				else{
					arlist_prices.add(price_help);
					help_list = new ArrayList<Result>();
					price_map.put(price_help,help_list);
				}
				help_list.add(results.get(i));
			}
			//sorting prices right
			LinkedHashMap<Double, List<Result>> final_hmap = new LinkedHashMap<>();
			if (inner_sort.equals("price|ASC"))
				Collections.sort(arlist_prices);
			else
				Collections.sort(arlist_prices, Collections.reverseOrder());
			for(i=0;i<arlist_prices.size();i++){
				final_hmap.put(arlist_prices.get(i),price_map.get(arlist_prices.get(i)));
			}
			map.put("start", start);
			map.put("count", count);
			map.put("total", final_hmap.size());
			map.put("prices",final_hmap);
			//return null;
			
            Gson gson = new Gson();
            String json = gson.toJson(map);
            return json;
		
		}catch(Exception e){
			System.out.println(e);
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid values eksoterika");

        }
    }
        boolean isValidDate(String input) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                System.out.println(format.parse(input));
                return true;
            }
            catch(ParseException e){
                return false;
            }
        }
	}