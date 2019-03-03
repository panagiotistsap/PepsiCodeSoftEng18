package gr.gradle.demo.data.model;

import java.util.Objects;

public class Result {

    private final Double price;
    private final String date;
    private final String productName;
    private final Long shopId;
    private final Long productId;
    private final String productTags;
    private final String shopTags;
    private final String shopAddress;    
    private final String shopName;
    
    public Result(Double price, String mydate, String product_name, Long shopid, Long productsid, String pr_tags, String sh_tags,
        String address, String sh_name ){
    	this.price = price;
    	this.shopId = shopid;
        this.productId = productsid;
        this.productName = product_name;
        this.date  = mydate;
        this.productTags = pr_tags;
        this.shopTags = sh_tags;
        this.shopAddress = address;
        this.shopName = sh_name;

    }

    public Double getprice(){
        return this.price;
    }

    public String gettags(){
        return productTags+","+shopTags;

    }

    
}