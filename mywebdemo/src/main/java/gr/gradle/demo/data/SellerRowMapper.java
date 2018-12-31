package gr.gradle.demo.data;

import gr.gradle.demo.data.model.Seller;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerRowMapper implements RowMapper {

    @Override
    public Seller mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id            = rs.getLong("id");
        String name        = rs.getString("name");
        String address     = rs.getString("address");
        Double Ing         = rs.getDouble("Ing");
        Double Iat         = rs.getDouble("Iat");
        String tags        = rs.getString("Tags");
        boolean withdrawn  = rs.getBoolean("withdrawn");

        return new Seller(id, name, address, Ing, Iat, tags, withdrawn);
    }

}
