package onlineShop.PCTech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller

public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @GetMapping("/inreg-form")
    public ModelAndView register(@RequestParam("email") String email,
                                 @RequestParam("psw") String psw,
                                 @RequestParam("pswRepeat") String pswRepeat) {
        ModelAndView modelAndView = new ModelAndView("inreg");
        if (!psw.equals(pswRepeat)) {
            modelAndView.addObject("message","parolele nu sunt identice");
        }
        else{
            jdbcTemplate.update("insert into users values (null,?,?)",email,psw);
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/inreg")
    public ModelAndView inreg() {

        return new ModelAndView("inreg");
    }
    @GetMapping("/login-form")
    public ModelAndView login(@RequestParam(value = "email") String email,
                              @RequestParam(value = "psw") String psw) {
        ModelAndView modelAndView = new ModelAndView("login");
        List<User> userList = jdbcTemplate.query("select * from users where email ='" + email +"';",new UserRowMapper());
        if(userList.size()==0){
        modelAndView.addObject("message","Email sau parola incorecte");
        }
        if(userList.size()>1){
            modelAndView.addObject("message","Email sau parola incorecte");
        }
        if(userList.size()==1){
            User userFromDatabase= userList.get(0);
            if(!userFromDatabase.getPassword().equals(psw)){
                modelAndView.addObject("message","Email sau parola incorecte");
            }
            else {
                modelAndView= new ModelAndView("redirect:/index");
            }
        }
        return modelAndView;
    }
}
