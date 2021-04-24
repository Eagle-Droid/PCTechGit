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


    @GetMapping("/")
    public ModelAndView frontpage() {
        ModelAndView modelAndView = new ModelAndView("index");
        loggedUser(modelAndView);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);
        List<Product> products = productDAO.findByCategory(0);
        return getModelAndView(modelAndView, products);
    }


    @GetMapping("/componente")
    public ModelAndView componente() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(6);
        return getModelAndView(modelAndView, products);
    }

    public ModelAndView getModelAndView(ModelAndView modelAndView, List<Product> products) {
        loggedUser(modelAndView);
        modelAndView.addObject("products",products);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);

        return modelAndView;
    }

    public void loggedUser(ModelAndView modelAndView) {
        UserController.isLoggedView(modelAndView, userSession);
    }

    @GetMapping("/laptop-gaming")
    public ModelAndView laptopGaming() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(2);
        return getModelAndView(modelAndView, products);
    }

    @GetMapping("/laptop-notebook")
    public ModelAndView laptopNotebook() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(1);
        return getModelAndView(modelAndView, products);
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        UserController.isLoggedView(modelAndView, userSession);
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);
        if(userSession.getUserId()==0){

            return modelAndView;
        }
        else{
            return new ModelAndView("redirect:/");
        }
    }

    @GetMapping("/memorii-notebook")
    public ModelAndView memoriiNotebook() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(3);
        return getModelAndView(modelAndView, products);
    }

    @GetMapping("/pc_gaming")
    public ModelAndView pcGaming() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(5);
        return getModelAndView(modelAndView, products);
    }

    @GetMapping("/pc_scoala")
    public ModelAndView pcScoala() {
        ModelAndView modelAndView = new ModelAndView("componente");
        List<Product> products = productDAO.findByCategory(4);
        return getModelAndView(modelAndView, products);
    }
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        int productCount = 0;
        for(int quantityForProduct : userSession.getShoppigCart().values()){
            productCount = productCount +quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize",productCount);
        loggedUser(modelAndView);
        return modelAndView;
    }


}
