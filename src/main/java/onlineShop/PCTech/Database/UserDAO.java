package onlineShop.PCTech.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

        public List<User> findByEmail(String email){
            return jdbcTemplate.query("select * from users where email ='" + email + "';", new UserRowMapper());
        }
        public void updateDetails(int id,String firstName,String lastName,String address){
            jdbcTemplate.execute("update users set first_name='"+firstName+"', last_name='"+lastName+"',address='"+address+"' where id='"+id+"';");
        }
        public void updateUserPassword(int id, String password){
            jdbcTemplate.execute("update users set password='"+password+"'where id='"+id+"';");
        }
        public void save(String email, String password, String firstName,String lastName){
            jdbcTemplate.update("insert into users values (null,?,?,?,?,null)", email, password, firstName, lastName);
        }
}
