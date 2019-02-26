package gr.gradle.demo.data.model;

import java.util.Objects;

public class Price {

    private final long productid;
    private final long shopid;
    private final Double price;
    private final String datefrom;
    private final String dateto;
    
    public Price(long productid, long shopid, Double price, String datefrom, String dateto){
    	this.productid = productid;
    	this.shopid = shopid;
    	this.price = price;
    	this.datefrom = datefrom;
    	this.dateto = dateto;
    }

    public Double getPrice(){
        return this.price;
    }

    public long getPrid(){
        return this.productid;
    }

    public long getShid(){
        return this.shopid;
    }

    public String getdateto(){
        return this.dateto;
    }

    public String getdatefrom(){
        return this.datefrom;
    }
}