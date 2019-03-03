package gr.gradle.demo.data.model;

import java.util.Objects;

public class Seller {

    private final long id;
    private final String name;
    private final String address;
    private final Double lng;
    private final Double lat;
    private final String[] tags;
    private final boolean withdrawn;

    public Seller(long id, String name, String address,double Ing, Double Iat,String tags,boolean withdrawn) {
        this.id           = id;
        this.name         = name;
        this.address      = address;
        this.lng          = Ing;
        this.lat          = Iat;
        if (tags!=null)
            this.tags         = tags.split(",");
        else 
            this.tags = new String[0];
        this.withdrawn    = withdrawn;
    }

    public Seller(Seller cop,long id, String name, String address, 
            Double Ing, Double Iat, String tags, Boolean withdrawn) {
        this.id = cop.id;
        if (name!=null)
            this.name = name;
        else 
            this.name = cop.name;
        if (address!=null)
            this.address = address;
        else
            this.address = cop.address;
        if (Ing!=null)
            this.lng = Ing;
        else    
            this.lng = cop.lng;
        if (Iat!=null)
            this.lat = Iat;
        else
            this.lat = cop.lat;
        if (tags!=null)
            this.tags = tags.split(",");
        else
            this.tags = cop.tags;
        if (withdrawn!=null)
            this.withdrawn = withdrawn;
        else
            this.withdrawn = cop.withdrawn;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getIng() {
        return lng;
    }

    public Double getIat() {
        return lat;
    }

    public String getTags() {
        String res = "";
        for(int i=0; i<this.tags.length-1;i++)
            res = res + tags[i] + ","  ;
        if (tags.length>0)
            res = res + tags[tags.length-1];
        return res;
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return id == seller.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
