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

import java.text.DecimalFormat;
import java.util.*;


@Controller
public class ProductController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    UserSession userSession;

    @Autowired
    mainController MainController;

    @GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product");
        UserController.isLogged(modelAndView,userSession);
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
        DecimalFormat price= new DecimalFormat("#.##");
        double totalPrice=0;
        ModelAndView modelAndView = new ModelAndView("cart");
        UserController.isLogged(modelAndView,userSession);
        for (Map.Entry<Integer, Integer> entry : userSession.getShoppigCart().entrySet()) {
            int quantity = entry.getValue();
            int productId = entry.getKey();
            Product productFromDatabase = productDAO.findById(productId);
            CartProduct cartProduct = new CartProduct();
            cartProduct.setQuantity(quantity);
            cartProduct.setId(productFromDatabase.getId());
            cartProduct.setName(productFromDatabase.getName());
            cartProduct.setPhotoFile1(productFromDatabase.getPhotoFile1());
            cartProduct.setPrice(productFromDatabase.getPrice());
            cartProduct.setTotal(Double.parseDouble(price.format(productFromDatabase.getPrice()*quantity)));
            totalPrice+=cartProduct.getTotal();
            productsFromCart.add(cartProduct);

        }
        int productCount = 0;
        for (int quantityForProduct : userSession.getShoppigCart().values()) {
            productCount = productCount + quantityForProduct;
        }
        modelAndView.addObject("shoppingCartSize", productCount);
        modelAndView.addObject("products", productsFromCart);
        modelAndView.addObject("totalPrice",price.format(totalPrice));
        return modelAndView;
    }
//nu merge hasmap de id si qty
    @PostMapping ("/remove-from-cart")
    public ModelAndView removeFromCart(@RequestParam("quantity")List<Integer> qty,
                                       @RequestParam("productId") List<Integer> id){
        System.out.println("IDs=" + id);
        System.out.println("qtys=" + qty);


        for(int i=0;i<qty.size();i++){
            Integer quantity= qty.get(i);
            Integer productId= id.get(i);
            System.out.println(userSession.getShoppigCart());
            System.out.println(productId);
            System.out.println(quantity);
            userSession.updateProduct(productId,quantity);
        }

        return new ModelAndView("redirect:/cart");
    }
}
