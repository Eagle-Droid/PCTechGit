package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.Product;
import onlineShop.PCTech.Database.ProductDAO;
import onlineShop.PCTech.Security.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class mainController {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    UserSession userSession;

    @GetMapping("/index")
    public ModelAndView frontpage() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    @GetMapping("/componente")
    public ModelAndView componente() {
        List<Product> products = productDAO.findAll();
        ModelAndView modelAndView = new ModelAndView("componente");
        modelAndView.addObject("products",products);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);

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



}
