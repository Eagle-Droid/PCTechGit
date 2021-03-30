package onlineShop.PCTech;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.id=resultSet.getInt("id");
        person.name=resultSet.getString("name");
        person.email=resultSet.getString("email");
        return person;
    }
}
