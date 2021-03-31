package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.*;
import onlineShop.PCTech.Security.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/inreg-form")
    public ModelAndView register(@RequestParam("email") String email,
                                 @RequestParam("psw") String psw,
                                 @RequestParam("pswRepeat") String pswRepeat,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName) {
        ModelAndView modelAndView = new ModelAndView("inreg");
        if (!psw.equals(pswRepeat)) {
            modelAndView.addObject("message", "parolele nu sunt identice");
            return modelAndView;
        } else {
            try {
                userService.save(email, psw, firstName, lastName);
            } catch (InvalidPassword invalidPassword) {
                String messageException = invalidPassword.getMessage();
                modelAndView.addObject("message",messageException);
                return  modelAndView;
            }
            return new ModelAndView("login");
        }

    }

    @GetMapping("/inreg")
    public ModelAndView inreg() {

        return new ModelAndView("inreg");
    }

    @GetMapping("/login-form")
    public ModelAndView login(@RequestParam(value = "email") String email,
                              @RequestParam(value = "psw") String psw,
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
            if (!userFromDatabase.getPassword().equals(psw)) {
                modelAndView.addObject("message", "Email sau parola incorecte");
            } else {
                userSession.setUserId(userFromDatabase.getId());
                modelAndView = new ModelAndView("redirect:/userDetails");//trb sa redirectionez la paginile unui user logat
            }
        }
        return modelAndView;
    }

    @GetMapping("/userDetails")
    public ModelAndView userDetails(HttpServletRequest request) {
        if (userSession.getUserId() == 0) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("userDetails");
    }
}
