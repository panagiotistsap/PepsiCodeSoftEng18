package gr.gradle.demo.data.model;

import java.util.Objects;

public class Price {

    private final long productid;
    private final long shopid;
    private final Double price;
    private final String datefrom;
    private final String dateto;
    
    public price(long productid, long shopid, Double price, String datefrom, String dateto){
    	this.productid = productid;
    	this.shopid = shopid;
    	this.price = price;
    	this.datefrom = datefrom;
    	this.dateto = dateto;
    }