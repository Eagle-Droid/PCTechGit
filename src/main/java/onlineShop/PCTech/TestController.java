package onlineShop.PCTech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.el.EvaluationListener;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/save")
    public ModelAndView save(@RequestParam(value = "name") String Name, @RequestParam(value = "email") String Email) {
        jdbcTemplate.update("insert into person values(null , ?, ?)",Name,Email);
        return new ModelAndView("result");
    }
    @GetMapping("/all")
    public ModelAndView all(){
        ModelAndView modelAndView = new ModelAndView("persons");
        List<Person> personList = jdbcTemplate.query("select * from person",new PersonRowMapper());
        modelAndView.addObject("persons", personList);
        return modelAndView;
    }
}
