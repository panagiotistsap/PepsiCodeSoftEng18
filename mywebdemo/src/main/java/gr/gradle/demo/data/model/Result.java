package gr.gradle.demo.data.model;

import java.util.Objects;

public class Result {

    private final Double price;
    private final String date;
    private final String product_name;
    private final Long shopid;
    private final Long productsid;
    private final String pr_tags;
    private final String sh_tags;
    private final String address;    
    
    public Result(Double price, String mydate, String product_name, Long shopid, Long productsid, String pr_tags, String sh_tags,
        String address ){
    	this.price = price;
    	this.shopid = shopid;
        this.productsid = productsid;
        this.product_name = product_name;
        this.date  = mydate;
        this.pr_tags = pr_tags;
        this.sh_tags = sh_tags;
        this.address = address;

    }

    public Double getprice(){
        return this.price;
    }

    public String gettags(){
        return pr_tags+","+sh_tags;

    }

    
}