package onlineShop.PCTech.Database;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product= new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getDouble("price"));
        product.setPhotoFile1(resultSet.getString("photo_file1"));
        product.setPhotoFile2(resultSet.getString("photo_file2"));
        product.setPhotoFile3(resultSet.getString("photo_file3"));
        return product;
    }
}
