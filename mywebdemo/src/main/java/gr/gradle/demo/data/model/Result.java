package gr.gradle.demo.data.model;

import java.util.Objects;

public class Result {

    private final Double price;
    private final String date;
    private final String productName;
    private final Long shopId;
    private final Long productId;
    private final String[] productTags;
    private final String[] shopTags;
    private final String shopAddress;    
    private final String shopName;
    private final Integer shopDist;
    
    
    public Result(Double price, String mydate, String product_name, Long shopid, Long productsid, String pr_tags, String sh_tags,
        String address, String sh_name ,Integer sd){
    	this.price = price;
    	this.shopId = shopid;
        this.productId = productsid;
        this.productName = product_name;
        this.date  = mydate;
        if (pr_tags!=null)
            this.productTags = pr_tags.split(",");
        else
            this.productTags = new String[0];
        if (sh_tags!=null)
            this.shopTags = sh_tags.split(",");
        else 
            this.shopTags = new String[0];
        this.shopAddress = address;
        this.shopName = sh_name;
        this.shopDist = sd;

    }

    public Double getprice(){
        return this.price;
    }

    public String gettags(){
        String res = "";
        for(int i=0; i<this.productTags.length-1;i++)
            res = res + productTags[i] + ","  ;
        if (productTags.length>0)
            res = res + this.productTags[productTags.length-1];
        if (shopTags.length>0)
            res = res + ",";
        else    
            return res;
        for(int i=0; i<this.shopTags.length-1;i++)
            res = res + shopTags[i] + ","  ;
        if (shopTags.length>0)
            res = res + shopTags[shopTags.length-1];
        return res;
    }

    
}