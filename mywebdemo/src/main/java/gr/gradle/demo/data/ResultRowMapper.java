package gr.gradle.demo.data;

import gr.gradle.demo.data.model.Result;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultRowMapper implements RowMapper {

    @Override
    public Result mapRow(ResultSet rs, int rowNum) throws SQLException {

        Double price       = rs.getDouble("sells.price");
        String productName = rs.getString("product.name");
        long productId     = rs.getLong("product.id");
        String productTags   = rs.getString("product.tags");
        long shopId        = rs.getLong("parkinglots.id");
        String shopName    = rs.getString("parkinglots.name");
        String shopTags    = rs.getString("parkinglots.tags");
        String shopAddress = rs.getString("parkinglots.address");
        

        return new Result(price,null,productName,shopId,productId,productTags,shopTags,shopAddress);
    }

}
