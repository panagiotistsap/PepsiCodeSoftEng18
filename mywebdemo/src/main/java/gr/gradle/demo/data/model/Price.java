package gr.gradle.demo.data.model;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.text.ParseException;

public class Price {

    private final Long productId;
    private final String shopId;
    private final Double price;
    private final String dateFrom;
    private final String dateTo;
    
    public Price(long productid, long shopid, Double price, String datefrom, String dateto){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.productId = productid;
    	this.shopId = String.valueOf(shopid);
        this.price = price;
        this.dateFrom = datefrom;
        this.dateTo = dateto;
        /*Date d1 = null;
        Date d2 = null;
        String d11=null;
        String d21=null;
		format.setLenient(false);
		try {
            d1 = format.parse(datefrom);
            d2 = format.parse(dateto);
            System.out.println(d1);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            d21 = dateFormat.format(d2);
            d11 = dateFormat.format(d1); //2016/11/16 12:08:43

		}
		catch(ParseException e){
            System.out.println("Haaaaaa Got him");
			
        }
        this.dateFrom = d11;
        this.dateTo = d21;
    	*/
    }

    public Double getPrice(){
        return this.price;
    }

    public long getPrid(){
        return this.productId;
    }

    public String getShid(){
        return this.shopId;
    }

    public String getdateto(){
        return this.dateTo;
    }

    public String getdatefrom(){
        return this.dateFrom;
    }
}