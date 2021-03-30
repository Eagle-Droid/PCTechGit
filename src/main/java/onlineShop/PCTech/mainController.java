package onlineShop.PCTech;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class mainController {
    @GetMapping("/index")
    public ModelAndView frontpage() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @GetMapping("/componente")
    public ModelAndView componente() {
        ModelAndView modelAndView = new ModelAndView("componente");
        return modelAndView;
    }

    @GetMapping("/laptop-gaming")
    public ModelAndView laptopGaming() {
        ModelAndView modelAndViewComponente = new ModelAndView("laptop-gaming");
        return modelAndViewComponente;
    }

    @GetMapping("/laptop-notebook")
    public ModelAndView laptopNotebook() {
        ModelAndView modelAndViewComponente = new ModelAndView("laptop-notebook");
        return modelAndViewComponente;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndViewComponente = new ModelAndView("login");
        return modelAndViewComponente;
    }

    @GetMapping("/memorii-notebook")
    public ModelAndView memoriiNotebook() {
        ModelAndView modelAndViewComponente = new ModelAndView("memorii-notebook");
        return modelAndViewComponente;
    }

    @GetMapping("/pc_gaming")
    public ModelAndView pcGaming() {
        ModelAndView modelAndViewComponente = new ModelAndView("pc_gaming");
        return modelAndViewComponente;
    }

    @GetMapping("/pc_scoala")
    public ModelAndView pcScoala() {
        ModelAndView modelAndViewComponente = new ModelAndView("pc_scoala");
        return modelAndViewComponente;
    }

    @GetMapping("/cart")
    public ModelAndView cart() {
        ModelAndView modelAndViewComponente = new ModelAndView("cart");
        return modelAndViewComponente;
    }

}
