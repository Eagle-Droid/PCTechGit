package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.*;
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

    @Autowired
    UserService userService;
    @Autowired
    OrderDAO orderDAO;

    @GetMapping("/product")
    public ModelAndView product(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product");
        UserController.isLoggedView(modelAndView,userSession);
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
        UserController.isLoggedView(modelAndView,userSession);

        int productCount = 0;
        for (int quantityForProduct : userSession.getShoppigCart().values()) {
            productCount = productCount + quantityForProduct;
        }
        if(userSession.getShoppigCart().size()==0){
            ModelAndView modelAndViewEmptyCart= new ModelAndView("emptyCart");
            modelAndViewEmptyCart.addObject("shoppingCartSize", productCount);
            UserController.isLoggedView(modelAndViewEmptyCart,userSession);
            return modelAndViewEmptyCart;
        }
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
        if(userSession.getUserId()!=0){

            String email = userSession.getEmail();
            List<User> userList = userService.findByEmail(email);
            String firstName = userList.get(0).getFirstName();
            String lastName = userList.get(0).getLastName();
            String address = userList.get(0).getAddress();
            modelAndView.addObject("firstName",firstName);
            modelAndView.addObject("lastName",lastName);
            modelAndView.addObject("address",address);
        }

        modelAndView.addObject("shoppingCartSize", productCount);
        modelAndView.addObject("products", productsFromCart);
        modelAndView.addObject("totalPrice",price.format(totalPrice));
        return modelAndView;
    }
//nu merge hasmap de id si qty
    @PostMapping (value = "/remove-from-cart",params = {})
    public ModelAndView removeFromCart(@RequestParam("quantity")List<Integer> qty,
                                       @RequestParam("productId") List<Integer> id){


        for(int i=0;i<qty.size();i++){
            Integer quantity= qty.get(i);
            Integer productId= id.get(i);
            userSession.updateProduct(productId,quantity);
        }

        return new ModelAndView("redirect:/cart");
    }
    @PostMapping (value = "/order",params = "totalPrice")
    public ModelAndView sendOrder(@RequestParam("totalPrice") double total,
                                  @RequestParam("address") String adress){

        orderDAO.newOrder(userSession.getUserId(), userSession.getShoppigCart(), adress,total);
        userSession.clearShoppingCart();
        return new ModelAndView("redirect:/cart");
    }
    @GetMapping("/clearer")
    public ModelAndView clearTheCart(){
        userSession.clearShoppingCart();
        return new ModelAndView("redirect:/cart");
    }
}
