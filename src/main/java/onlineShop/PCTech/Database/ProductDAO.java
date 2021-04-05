package onlineShop.PCTech.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> findAll(){
        return jdbcTemplate.query("select * from product", new ProductRowMapper());
    }
    public List<Product> findById(Integer id){
        return jdbcTemplate.query("select * from product where id ="+id, new ProductRowMapper());
    }
    public  List<Specification> findByForeignKey(Integer foreignKey){
        return jdbcTemplate.query("select * from pctech.description inner join pctech.product on pctech.description.foreign_key = pctech.product.id where pctech.product.id ="+ foreignKey +";", new SpecsRowMapper());

    }
}