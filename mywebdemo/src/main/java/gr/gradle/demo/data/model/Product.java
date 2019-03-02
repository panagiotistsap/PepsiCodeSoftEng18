package gr.gradle.demo.data.model;
import java.util.Arrays;
import java.util.Objects;

public class Product {

    private final long id;
    private final String name;
    private final String description;
    private final String category;
    private final boolean withdrawn;
    private final String[] tags;

    public Product(long id, String name, String description, String category, Boolean withdrawn, String tags) {
        this.id          = id;
        this.name        = name;
        if (description==null)
            this.description = null;
        else
            this.description = description;
        this.category    = category;
        if (withdrawn==null)
            this.withdrawn = false;
        else
            this.withdrawn = withdrawn;
        if(tags!=null){
            this.tags = tags.split(","); //Arrays.copyOfRange(tags.split(","), 1, tags.split(",").length);;
        }
        else
            this.tags    = new String[0];
    }
    
    public Product(Product cop,long id, String name, String description, String category, Boolean withdrawn, String tags) {
        this.id = cop.id;
        if (name!=null)
            this.name = name;
        else 
            this.name = cop.name;
        if (description!=null)
            this.description = description;
        else
            this.description = cop.description;

        if (category!=null)
            this.category = category;
        else    
            this.category = cop.category;
        if (withdrawn!=null)
            this.withdrawn = withdrawn;
        else
            this.withdrawn = cop.withdrawn;
        if (tags!=null)
            this.tags = tags.split(",");
        else
            this.tags = cop.tags;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }

    public String getTags() {
        String res = "";
        for(int i=0; i<this.tags.length-1;i++)
            res = res + tags[i] + ","  ;
        if (tags.length>0)
            res = res + tags[tags.length-1];
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
