package gr.gradle.demo.data;


import gr.gradle.demo.data.model.Product;
import gr.gradle.demo.data.model.Seller;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.restlet.resource.ResourceException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.restlet.data.Status;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class DataAccess {

    private static final Object[] EMPTY_ARGS = new Object[0];

    private static final int MAX_TOTAL_CONNECTIONS = 16;
    private static final int MAX_IDLE_CONNECTIONS = 8;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setup(String driverClass, String url, String user, String pass) throws SQLException {

        //initialize the data source
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClass);
        bds.setUrl(url);
        bds.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        bds.setMaxIdle(MAX_IDLE_CONNECTIONS);
        bds.setUsername(user);
        bds.setPassword(pass);
        bds.setValidationQuery("SELECT 1");
        bds.setTestOnBorrow(true);
        bds.setDefaultAutoCommit(true);

        //check that everything works OK
        bds.getConnection().close();

        //initialize the jdbc template utilitiy
        jdbcTemplate = new JdbcTemplate(bds);
    }

    public List<Product> getProducts(Limits limits,String sort,String status) {
        //TODO: Support limits DONE
        int start = (int)limits.getStart();
        int count = limits.getCount();
        String stat,srt;
        if (status==null || status.equals("ALL")) stat="";
        else if (status.equals("ACTIVE")) stat="where withdrawn=false";
        else stat="where withdrawn=0";

        if (sort==null || sort.equals("id|DESC")) srt="order by id desc";
        else if (sort.equals("id|ASC")) srt="order by id";
        else if (sort.equals("name|ASC")) {srt="order by name"; System.out.println("geiaaaaaaaa");}
        else srt="order by name desc";

        List<Product> helping =  jdbcTemplate.query("select * from product " + stat +" "+ srt, EMPTY_ARGS, new ProductRowMapper());
        if (start>helping.size() || helping.size()==0)
          return null;
        if (count>helping.size())
          return helping.subList(start,helping.size()-1);
        return helping.subList(start,count);
    }

    public Product addProduct(String name, String description, String category, boolean withdrawn, String tags ) {
        //Create the new product record using a prepared statement
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "insert into product(name, description, category, withdrawn, tags) values(?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, category);
                ps.setBoolean(4, withdrawn);
                ps.setString(5, tags);
                return ps;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int cnt = jdbcTemplate.update(psc, keyHolder);

        if (cnt == 1) {
            //New row has been added
            Product product = new Product(
                keyHolder.getKey().longValue(), //the newly created project id
                name,
                description,
                category,
                withdrawn,
                tags
            );
            return product;

        }
        else {
            throw new RuntimeException("Creation of Product failed");
        }
    }

    public Optional<Product> getProduct(long id) {
        Long[] params = new Long[]{id};
        List<Product> products = jdbcTemplate.query("select * from product where id = ?", params, new ProductRowMapper());
        if (products.size() == 1)  {
            return Optional.of(products.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<Product> putProduct(Long id,String name,String description,Boolean withdrawn,String tags,String category ){
        Optional<Product> pro = getProduct(id);
        Product product = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + id));
        Product pro2 = new Product(id,name,description,category,withdrawn,tags);
        jdbcTemplate.update("update product set name=?,description=?,withdrawn=?,tags=?,category=?",pro2.getName(),pro2.getDescription(),pro2.isWithdrawn(),pro2.getTags(),pro2.getCategory());
        return Optional.of(pro2);
            
    }

    public Optional<Product> patchProduct(Long id,String name,String description,Boolean withdrawn,String tags,String category ){
        Optional<Product> pro = getProduct(id);
        Product product = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + id));
        Product pro2 = new Product(product,id,name,description,category,withdrawn,tags);
        jdbcTemplate.update("update product set name=?,description=?,withdrawn=?,tags=?,category=?",pro2.getName(),pro2.getDescription(),pro2.isWithdrawn(),pro2.getTags(),pro2.getCategory());
        return Optional.of(pro2);
            
    }
    
    public Boolean deleteProduct(long id,int rights){
        String str_id = String.valueOf(id);
        if (rights==0){
            jdbcTemplate.update("update product set withdrawn=1 where id=?",id);
            return true;
        }
        else{
            List<Product> products = jdbcTemplate.query("select * from product where id = "+str_id, EMPTY_ARGS, new ProductRowMapper());
            if (products.size() == 1)  {
                jdbcTemplate.update("delete from product where id= "+str_id, EMPTY_ARGS);
                return true;
            }
            else
                return false;
        }
    }

    public List<Seller> getSellers(Limits limits,String sort,String status) {
        //Support limits DONE
        int start = (int)limits.getStart();
        int count = limits.getCount();
        String stat,srt;
        if (status==null || status.equals("ALL")) stat="";
        else if (status.equals("ACTIVE")) stat="where withdrawn=0";
        else stat="where withdrawn=1";

        if (sort==null || sort.equals("id|DESC")) srt="order by id desc";
        else if (sort.equals("id|ASC")) srt="order by id";
        else if (sort.equals("name|ASC")) {srt="order by name"; System.out.println("geiaaaaaaaa");}
        else srt="order by name desc";

        List<Seller> helping =  jdbcTemplate.query("select * from seller "+stat+" "+srt, EMPTY_ARGS, new ProductRowMapper());
        if (start>helping.size() || helping.size()==0)
          return null;
        if (count>helping.size())
          return helping.subList(start,helping.size()-1);
        return helping.subList(start,count);
    }

    public Optional<Seller> getSeller(long id) {
        Long[] params = new Long[]{id};
        List<Seller> seller = jdbcTemplate.query("select * from seller where id = ?", params, new ProductRowMapper());
        if (seller.size() == 1)  {
            return Optional.of(seller.get(0));
        }
        else {
            return Optional.empty();
        }
    }

    public Seller addSeller(String name, String address, Double Ing, Double Iat, String tags, boolean withdrawn) {
        //Create the new product record using a prepared statement
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "insert into seller(name, address, Ing, Iat, tags,withdrawn) values(?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setDouble(3, Ing);
                ps.setDouble(4, Iat);
                ps.setString(5, tags);
                ps.setBoolean(6,withdrawn);
                return ps;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int cnt = jdbcTemplate.update(psc, keyHolder);

        if (cnt == 1) {
            //New row has been added
            Seller seller = new Seller(
                keyHolder.getKey().longValue(), //the newly created project id
                name,
                address,
                Ing,
                Iat,
                tags,
                withdrawn
            );
            return seller;

        }
        else {
            throw new RuntimeException("Creation of Seller failed");
        }
    }
    
    public Boolean login(String username,String password,String token){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
            PreparedStatement ps = c.prepareStatement("select admin from users where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String admin = Integer.toString(rs.getInt("admin"));
                jdbcTemplate.update("insert into logged (token,admin) values (?,?)",token,Integer.parseInt(admin));
                return true;
            }
            return false;
        }catch (ClassNotFoundException | SQLException e) {
            return false;
            
            
		}

    }

    public Boolean logout(String token){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
            PreparedStatement ps = c.prepareStatement("select admin from logged where token=?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String admin = Integer.toString(rs.getInt("admin"));
                jdbcTemplate.update("delete from logged where token=?",token);
                return true;
            }
            return false;
        }catch (ClassNotFoundException | SQLException e) {
            return false;
            
            
		}

    }  

    public int isloggedin(String token){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
            PreparedStatement ps = c.prepareStatement("select admin from logged where token=?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("admin");
            }
            return -1;
        }catch (ClassNotFoundException | SQLException e) {
            return -1;
            
            
		}
    }
        


}
