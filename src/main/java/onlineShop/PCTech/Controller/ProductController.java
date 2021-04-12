package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.Product;
import onlineShop.PCTech.Database.ProductDAO;
import onlineShop.PCTech.Database.Specification;
import onlineShop.PCTech.Security.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ProductController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    UserSession userSession;

    @GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productDAO.findById(id);
        modelAndView.addObject("product", product);
        List<Specification> specifications = productDAO.findByForeignKey(id);
        modelAndView.addObject("specs", specifications);
        int productCount = 0;
        for (int quantityForProduct : userSession.getShoppigCart().values()) {
            productCount = productCount + quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize", productCount);
        return modelAndView;
    }

    @PostMapping("/add-to-cart")
    public ModelAndView addToCart(@RequestParam("productId") Integer id,
                                  @RequestParam("quantity") int quantity) {
        for (int i = 0; i < quantity; i++) {

            userSession.addNewProduct(id);
        }
        return new ModelAndView("redirect:/cart");
       //return new ModelAndView("redirect:product?id=" + id);
    }

    @GetMapping("cart")
    public ModelAndView cart() {
        List<CartProduct> productsFromCart = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView("cart");
        for (Map.Entry<Integer, Integer> entry : userSession.getShoppigCart().entrySet()) {
            int quantity = entry.getValue();
            int productId = entry.getKey();
            Product productFromDatabase = productDAO.findById(productId);
            CartProduct cartProduct = new CartProduct();
            cartProduct.setQuantity(quantity);
            cartProduct.setId(productFromDatabase.getId());
            cartProduct.setName(productFromDatabase.getName());
            cartProduct.setPhotoFile1(productFromDatabase.getPhotoFile1());

            productsFromCart.add(cartProduct);

        }
        modelAndView.addObject("products", productsFromCart);
        modelAndView.addObject("form",productsFromCart);
        return modelAndView;
    }
//nu merge
    @PostMapping ("/remove-from-cart")
    public ModelAndView removeFromCart(@RequestParam("quantity") int qty,
                                       @RequestParam("productId") Integer id){


        userSession.removeProduct(id,qty);
        return new ModelAndView("redirect:/cart");
    }
}
