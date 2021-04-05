package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.Product;
import onlineShop.PCTech.Database.ProductDAO;
import onlineShop.PCTech.Database.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
@Autowired
    ProductDAO productDAO;
@GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id){
    ModelAndView modelAndView = new ModelAndView("product");
    List<Product> product = productDAO.findById(id);
    modelAndView.addObject("product",product);
    List<Specification> specifications = productDAO.findByForeignKey(id);
    modelAndView.addObject("specs",specifications);
    //modelAndView.addObject("specs",specifications);
    return modelAndView;

}
}
