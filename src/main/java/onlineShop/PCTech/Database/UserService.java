package onlineShop.PCTech.Database;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void updateUser(int id,String firstName,String lastName,String address){
        userDAO.updateDetails(id,firstName,lastName,address);
    }
    public void userUpdatePassword(int id, String newPassword) throws InvalidPassword{
        if(newPassword.length()<10){
            throw new InvalidPassword("Parola prea slaba");
        }
        String newPasswordMD5 = DigestUtils.md5Hex(newPassword);
        userDAO.updateUserPassword(id, newPasswordMD5);
    }
    public List<User> findByEmail(String email){
        return userDAO.findByEmail(email);
    }
}
