package gr.gradle.demo.data.model;

import java.util.Objects;

public class Seller {

    private final long id;
    private final String name;
    private final String address;
    private final Double Ing;
    private final Double Iat;
    private final String tags;
    private final boolean withdrawn;

    public Seller(long id, String name, String address,double Ing, Double Iat,String tags,boolean withdrawn) {
        this.id           = id;
        this.name         = name;
        this.address      = address;
        this.Ing          = Ing;
        this.Iat          = Iat;
        this.tags         = tags;
        this.withdrawn    = withdrawn;
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
        return Ing;
    }

    public Double getIat() {
        return Iat;
    }

    public String getTags() {
        return tags;
    }

    public boolean getWithdrawn() {
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
