package gr.gradle.demo.data;
import java.security.MessageDigest;
import java.math.BigInteger;

import gr.gradle.demo.data.model.Product;
import gr.gradle.demo.data.model.Seller;
import gr.gradle.demo.api.JsonPriceRepresentation;
import gr.gradle.demo.data.model.Price;
import gr.gradle.demo.data.model.Result;
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
        int start = (int)limits.getStart();
        int count = limits.getCount();
        String stat,srt;
        if (status==null || status.equals("ACTIVE")) stat="where withdrawn=false";
        else if (status.equals("ALL")) stat="";
        else if (status.equals("WITHDRAWN"))
            stat="where withdrawn=true";
        else
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid status Values");

        if (sort==null || sort.equals("id|DESC")) srt="order by id desc";
        else if (sort.equals("id|ASC")) srt="order by id";
        else if (sort.equals("name|ASC")) {srt="order by name"; System.out.println("geiaaaaaaaa");}
        else if (sort.equals("name|DESC"))
            srt="order by name desc";
        else
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid sort Values");

        List<Product> helping =  jdbcTemplate.query("select * from product " + stat +" "+ srt, EMPTY_ARGS, new ProductRowMapper());
        if (start>helping.size() || helping.size()==0)
          return null;
        if (count>helping.size())
          return helping.subList(start,helping.size());
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
        System.out.println(withdrawn);
        Product pro2 = new Product(id,name,description,category,withdrawn,tags);
        jdbcTemplate.update("update product set name=?,description=?,withdrawn=?,tags=?,category=? where id=?",pro2.getName(),pro2.getDescription(),pro2.isWithdrawn(),pro2.getTags(),pro2.getCategory(),id);
        return Optional.of(pro2);
    }

    public Optional<Product> patchProduct(Long id,String name,String description,Boolean withdrawn,String tags,String category ){
        Optional<Product> pro = getProduct(id);
        Product product = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + id));
        Product pro2 = new Product(product,id,name,description,category,withdrawn,tags);
        jdbcTemplate.update("update product set name=?,description=?,withdrawn=?,tags=?,category=? where id=?",pro2.getName(),pro2.getDescription(),pro2.isWithdrawn(),pro2.getTags(),pro2.getCategory(),id);
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
        if (status==null || status.equals("ACTIVE")) stat="where withdrawn=0";
        else if (status.equals("WITHDRAWN")) stat="where withdrawn=1";
        else if (status.equals("ALL")) stat="";
        else throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid status Values");

        if (sort==null || sort.equals("id|DESC")) srt="order by id desc";
        else if (sort.equals("id|ASC")) srt="order by id";
        else if (sort.equals("name|ASC")) {srt="order by name"; System.out.println("geiaaaaaaaa");}
        else if (sort.equals("name|DESC")) srt="order by name desc";
        else throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, "Invalid sort Values");

        List<Seller> helping =  jdbcTemplate.query("select * from parkinglots " + stat +" "+ srt, EMPTY_ARGS, new SellerRowMapper());
        if (start>helping.size() || helping.size()==0){
            //System.out.println("mpika edw");
            return null;
        }
        if (count>helping.size())
          return helping.subList(start,helping.size());
        return helping.subList(start,count);
    }

    public Optional<Seller> getSeller(long id) {
        Long[] params = new Long[]{id};
        List<Seller> seller = jdbcTemplate.query("select * from parkinglots where id = ?", params, new SellerRowMapper());
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
                        "insert into parkinglots(name, address, Ing, Iat, tags,withdrawn) values(?, ?, ?, ?, ?, ?)",
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
    
    public Boolean login(String username,String plaintext,String token){
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            //Decoding
            BigInteger bigInt = new BigInteger(1,digest);
            String password = bigInt.toString(16);
            while(password.length()<32)
                password = "0"+password;
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
            PreparedStatement ps = c.prepareStatement("select admin,id from users where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String admin = Integer.toString(rs.getInt("admin"));
                String his_id = Integer.toString(rs.getInt("id"));
                jdbcTemplate.update("insert into logged (token,admin,user_id) values (?,?,?)",token,Integer.parseInt(admin),Integer.parseInt(his_id));
                return true;
            }
            return false;
        }catch (Exception e) {
            System.out.println(e);
            return false;
            
            
		}
    }

    public Boolean logout(String plaintext){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            //Decoding
            BigInteger bigInt = new BigInteger(1,digest);
            String token = bigInt.toString(16);
            while(token.length()<32)
                token = "0"+token;
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
        }catch (Exception e) {
            return false;
            
            
		}
    }  

    public int isloggedin(String plaintext){
        if (plaintext==null)
            return -1;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(plaintext.getBytes());
            byte[] digest = m.digest();
            //Decoding
            BigInteger bigInt = new BigInteger(1,digest);
            String token = bigInt.toString(16);
            while(token.length() < 32 )
                token = "0"+token;
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
            PreparedStatement ps = c.prepareStatement("select admin from logged where token=?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("admin");
            }
            return -1;
        }
        catch (Exception e) {
            return -1;
        }
    }
        
    public Boolean deleteShop(long id,int rights){
        String str_id = String.valueOf(id);
        List<Product> products = jdbcTemplate.query("select * from parkinglots where id = "+str_id, EMPTY_ARGS, new SellerRowMapper());
        if (products.size()==0)
            return false;
        if (rights==0)
            jdbcTemplate.update("update parkinglots set withdrawn=1 where id=?",id);
        else
            jdbcTemplate.update("delete from parkinglots where id= "+str_id, EMPTY_ARGS);
        return true;
    }

    public Optional<Seller> putShop(Long id,String name,String address,Double Ing,Double Iat,String tags,Boolean withdrawn){
        Optional<Seller> pro = getSeller(id);
        Seller seller = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Seller not found - id: " + id));
        Seller pro2 = new Seller(id,name,address,Ing,Iat,tags,withdrawn);
        jdbcTemplate.update("update parkinglots set name=?,address =?,withdrawn=?,tags=?,Ing=?,Iat=? where id=?",
                pro2.getName(),pro2.getAddress(),pro2.isWithdrawn(),pro2.getTags(),pro2.getIng(),pro2.getIat(),id);
        return Optional.of(pro2);
    }

    public Optional<Seller> patchShop(Long id,String name,String address,Double Ing,Double Iat,String tags,Boolean withdrawn){
        Optional<Seller> pro = getSeller(id);
        Seller seller = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Seller not found - id: " + id));
        Seller pro2 = new Seller(seller,id,name,address,Ing,Iat,tags,withdrawn);
        jdbcTemplate.update("update parkinglots set name=?,address =?,withdrawn=?,tags=?,Ing=?,Iat=? where id=?",
                pro2.getName(),pro2.getAddress(),pro2.isWithdrawn(),pro2.getTags(),pro2.getIng(),pro2.getIat(),id);
        return Optional.of(pro2);
    }

    public Price postPrice(Long l_productid, Long l_shopid,Double d_price,String date_from, String date_to){
        //Create the new product record using a prepared statement
        Optional<Seller> sel = getSeller(l_shopid);
        Optional<Product> pro = getProduct(l_productid);
        Seller seller = sel.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Seller not found - id: " + l_shopid));
        Product prod = pro.orElseThrow(() -> new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Product not found - id: " + l_productid));
        jdbcTemplate.update("delete from sells where (datefrom between ? and ? ) and (dateto between ? and ?) and sellerid=? and productid=?",
        date_from,date_to,date_from,date_to,l_shopid,l_productid);
        jdbcTemplate.update("update sells set dateto=? - interval 1 day where (dateto between ? and ?) and sellerid=? and productid=?",
        date_from,date_from,date_to,l_shopid,l_productid);
        jdbcTemplate.update("update sells set datefrom=? + interval 1 day where (datefrom between ? and ?) and sellerid=? and productid=?",
        date_to,date_from,date_to,l_shopid,l_productid);
        //  throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, "Shop or Product not found ");
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "insert into sells(sellerid, productid, price, datefrom, dateto) values(?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, l_shopid);
                ps.setLong(2, l_productid);
                ps.setDouble(3, d_price);
                ps.setString(4, date_from);
                ps.setString(5, date_to);
                return ps;
            }
        };

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int cnt = jdbcTemplate.update(psc, keyHolder);

        if (cnt == 1) {
            //New row has been added
            Price price = new Price(l_productid,l_shopid,d_price,date_from,date_to);
            return price;

        }
        else {
            throw new RuntimeException("Creation of Product failed");
        }


    }

    public List<Result> getResults(Limits limits,String[] sort_list,int geoDist,Double Lng, Double Lat,
        Long[] shopsid,Long[] productids,String [] tags,String datefrom, String dateto){
        //Support limits DONE
        int i;
        int start = (int)limits.getStart();
        int count = limits.getCount();
        String stat,srt;
        System.out.println(sort_list.length);
        //sort
        String order_string = "";
        for(i=0;i<sort_list.length-1;i++){
            String[] parts = sort_list[i].split("\\|");
            if (parts[0].equals("geo.dist"))
                parts[0]="geodist";
            System.out.println(parts[0]);
            if (!(parts[0].equals("price")))
                order_string = order_string + parts[0] + " "+parts[1]+",";   
        }
        System.out.println(order_string);
        i = sort_list.length-1;
        String[] parts = sort_list[i].split("\\|");
        if (parts[0].equals("geo.dist"))
            parts[0]="geodist";
        System.out.println(parts[0]);
        if (!(parts[0].equals("price"))){
            System.out.println("hello");
            order_string = order_string + parts[0] + " "+parts[1];   
        }
        else{
            if (order_string.length()>1)
            order_string = order_string.substring(0, order_string.length() - 1);
        }
        if (!(order_string.equals("")))
            order_string = " order by " + order_string;
        
        System.out.println(order_string);
        //dates
        String mysql_date1="(select adddate('1970-01-01',t4.i*10000 + t3.i*1000 + t2.i*100 + t1.i*10 + t0.i) date from"+
        "(select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t0,"+
        "(select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t1,"+
        "(select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t2,"+
        "(select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t3,"+
        "(select 0 i union select 1 union select 2 union select 3 union select 4 union select 5 union select 6 union select 7 union select 8 union select 9) t4) v";
        String mysql_date2="date between greatest(sells.datefrom,'"+datefrom+"') and least(sells.dateto,'"+dateto+"') and";
        /*
            if (datefrom!=null && dateto!=null){
                mysql_date="and sells.datefrom between '" + datefrom + "' and '"+dateto+"' ";
                mysql_date = mysql_date + "and sells.dateto between '" + datefrom + "' and '"+dateto+"' ";
        }*/
        //shop ids
        String mysql_shops="";
        if (shopsid!=null){
            mysql_shops="(";
            for(i=0;i<shopsid.length-1;i++)
                mysql_shops=mysql_shops+String.valueOf(shopsid[i])+",";
            mysql_shops=mysql_shops+String.valueOf(shopsid[shopsid.length-1])+")";           
        }
        System.out.println("ftanw7");
        if (mysql_shops!="")
            mysql_shops=" and parkinglots.id in "+mysql_shops+" ";
        //productsid
        String mysql_prods="";
        if (productids!=null){
            mysql_prods="(";
            for(i=0;i<productids.length-1;i++)
                mysql_prods=mysql_prods+String.valueOf(productids[i])+",";
            mysql_prods=mysql_prods+String.valueOf(productids[productids.length-1])+")";           
        }
        if (!mysql_prods.equals(""))
            mysql_prods=" and product.id in "+mysql_prods+" ";
        System.out.println("geodist:");
        System.out.println(geoDist);
        String geostring1="";
        String geostring2="";
        if (geoDist!=0){
            geostring1=",111.111 *"+
            "DEGREES(ACOS(LEAST(COS(RADIANS(parkinglots.Iat))"+
                 "* COS(RADIANS("+String.valueOf(Lat)+"))"+
                 "* COS(RADIANS(parkinglots.Ing - "+String.valueOf(Lng)+"))"+
                 "+ SIN(RADIANS(parkinglots.Iat))"+
                 "* SIN(RADIANS("+String.valueOf(Lat)+")), 1.0))) AS geodist";

            geostring2="having geodist<"+String.valueOf(geoDist);
        }
        System.out.println("ftanw8");
        System.out.println(order_string);
        System.out.println(mysql_prods);
        System.out.println(mysql_shops);
        

            System.out.println("select sells.price,product.name,product.id,product.tags"+
            ",parkinglots.id,parkinglots.name,parkinglots.tags,parkinglots.address,date"+geostring1+" "+
            "from "+mysql_date1+",product,parkinglots,sells where "+mysql_date2+" sells.sellerid=parkinglots.id and sells.productid=product.id " + 
            mysql_prods+" "+mysql_shops+" "+geostring2+order_string);
        
        List<Result> helping =  jdbcTemplate.query("select sells.price,product.name,product.id,product.tags"+
                                            ",parkinglots.id,parkinglots.name,parkinglots.tags,parkinglots.address,date"+geostring1+" "+
                                            "from "+mysql_date1+",product,parkinglots,sells where "+mysql_date2+" sells.sellerid=parkinglots.id and sells.productid=product.id " + 
                                            mysql_prods+" "+mysql_shops+" "+geostring2+order_string, EMPTY_ARGS, new ResultRowMapper());
        
        if (count>helping.size())
           return helping.subList(start,helping.size());
        return helping.subList(start,count);
      

        
    }

    public Boolean ch_un_avl(String new_username){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/softeng", "myuser", "pass"); // gets a new connection
        PreparedStatement ps = c.prepareStatement("select * from users where username=?");
        ps.setString(1, new_username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            return false;
        }
        return true;
        }catch(Exception e){
            return false;
        }
    }

    public Boolean signup(String un,String pas){
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "insert into users (username,password,admin) values (?,?,0)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, un);
                ps.setString(2, pas);
                return ps;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int cnt = jdbcTemplate.update(psc, keyHolder);
        if (cnt == 1)
            return true;
        else
            return false;
        
    }
    
}
