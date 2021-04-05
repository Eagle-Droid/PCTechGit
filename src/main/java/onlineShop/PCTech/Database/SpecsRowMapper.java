package onlineShop.PCTech.Database;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecsRowMapper implements RowMapper<Specification> {

    @Override
    public Specification mapRow(ResultSet resultSet, int i) throws SQLException {
        Specification specification = new Specification();
        specification.setId(resultSet.getInt("id"));
        specification.setForeignKey(resultSet.getInt("foreign_key"));
        specification.setFirstCol(resultSet.getString("firstcol"));
        specification.setSecondCol(resultSet.getString("secondcol"));
        return specification;
    }
}
