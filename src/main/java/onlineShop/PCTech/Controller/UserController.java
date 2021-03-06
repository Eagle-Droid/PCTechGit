package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.*;
import onlineShop.PCTech.Security.UserSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserSession userSession;
    @Autowired
    mainController mainController;

    @PostMapping("/register-form")
    public ModelAndView register(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("passwordRepeat") String passwordRepeat,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (!password.equals(passwordRepeat)) {
            modelAndView.addObject("message", "parolele nu sunt identice");
            return modelAndView;
        } else {
            try {
                userService.save(email, password, firstName, lastName);
            } catch (InvalidPassword invalidPassword) {
                String messageException = invalidPassword.getMessage();
                modelAndView.addObject("message",messageException);
                return  modelAndView;
            }
            return new ModelAndView("login");
        }

    }

    @GetMapping("/login-form")
    public ModelAndView login(@RequestParam(value = "email") String email,
                              @RequestParam(value = "password") String password,
                              HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        List<User> userList = userService.findByEmail(email);
        if (userList.size() == 0) {
            modelAndView.addObject("message", "Email sau parola incorecte");
        }
        if (userList.size() > 1) {
            modelAndView.addObject("message", "Email sau parola incorecte");
        }
        if (userList.size() == 1) {
            User userFromDatabase = userList.get(0);
            if (!userFromDatabase.getPassword().equals(DigestUtils.md5Hex(password))) {
                modelAndView.addObject("message", "Email sau parola incorecte");
            } else {
                userSession.setUserId(userFromDatabase.getId());
                userSession.setEmail(userFromDatabase.getEmail());
                modelAndView = new ModelAndView("redirect:/userDetails");//trb sa redirectionez la paginile unui user logat

            }
        }
        return modelAndView;
    }


    @GetMapping(value = "/userDetails",params = {})
    public ModelAndView userDetails() {
        ModelAndView modelAndView = new ModelAndView("userDetails");
        isLoggedView(modelAndView, userSession);
        if (userSession.getUserId() == 0) {

            return new ModelAndView("redirect:/login");
        }
        String email = userSession.getEmail();
        List<User> userList = userService.findByEmail(email);
        String firstName = userList.get(0).getFirstName();
        String lastName = userList.get(0).getLastName();
        String address = userList.get(0).getAddress();
        modelAndView.addObject("firstName",firstName);
        modelAndView.addObject("lastName",lastName);
        modelAndView.addObject("address",address);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);
        return modelAndView;
    }
    @GetMapping("/updateUserDetails")
    public ModelAndView updateUserDetails(@RequestParam(value = "firstName") String firstName,
                                          @RequestParam(value = "lastName") String lastName,
                                          @RequestParam(value = "address") String address){
        userService.updateUser(userSession.getUserId(), firstName,lastName,address);
        return new ModelAndView("redirect:/userDetails");

    }
    @PostMapping(value = "/userDetails",params = {"password","newPassword"})
    public ModelAndView updatePassword(@RequestParam(value = "password") String password,
                                       @RequestParam(value = "newPassword") String newPassword){
        ModelAndView modelAndView = new ModelAndView("userDetails");
        isLoggedView(modelAndView, userSession);
        if (userSession.getUserId() == 0) {

            return new ModelAndView("redirect:/login");
        }
        String email = userSession.getEmail();
        List<User> userList = userService.findByEmail(email);
        String firstName = userList.get(0).getFirstName();
        String lastName = userList.get(0).getLastName();
        String address = userList.get(0).getAddress();
        modelAndView.addObject("firstName",firstName);
        modelAndView.addObject("lastName",lastName);
        modelAndView.addObject("address",address);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);
        /*return modelAndView;*/

        User userFromDatabase = userList.get(0);

            if(!DigestUtils.md5Hex(password).equals(userFromDatabase.getPassword())){
                modelAndView.addObject("message", "Parola gresita");
                return modelAndView;
            }
            else{
                try {
                    userService.userUpdatePassword(userSession.getUserId(),newPassword);
                } catch (InvalidPassword invalidPassword) {
                    String messageException = invalidPassword.getMessage();
                    modelAndView.addObject("message",messageException);
                    return  modelAndView;
                }
            }
            return modelAndView;
    }
    public static void isLoggedView(ModelAndView modelAndView, UserSession userSession) {
        String userLogged;
        String userRegistered = "";
        String href;
        String hidden="";
        if (userSession.getUserId() == 0){
            userLogged="Logare";
            userRegistered="Inregistare";
            href="/login";
        }else{
            userLogged="Delogare";
            href="/logout";
            hidden="hidden";
        }
        modelAndView.addObject("hidden",hidden);
        modelAndView.addObject("userLogged",userLogged);
        modelAndView.addObject("userRegistered",userRegistered);
        modelAndView.addObject("href",href);
    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        userSession.logoff();
        return new ModelAndView("redirect:/");
    }
}
