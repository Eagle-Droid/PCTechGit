package onlineShop.PCTech.Database;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public void save(String email, String password, String firstName,String lastName) throws InvalidPassword{
        if(password.length()<10){
            throw new InvalidPassword("Parola prea slaba");
        }
        if(userDAO.findByEmail(email).size()>0){
            throw new InvalidPassword("Email existent");
        }
        String passwordMD5 = DigestUtils.md5Hex(password);
        userDAO.save(email, passwordMD5, firstName, lastName);
    }
    public List<User> findByEmail(String email){
        return userDAO.findByEmail(email);
    }
}
