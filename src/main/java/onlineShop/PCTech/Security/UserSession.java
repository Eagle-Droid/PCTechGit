package onlineShop.PCTech.Security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SessionScope
public class UserSession {
    private int userId;
        HashMap<Integer, Integer> shoppigCart= new HashMap<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashMap<Integer, Integer> getShoppigCart() {
        return shoppigCart;
    }

    public void setShoppigCart(HashMap<Integer, Integer> shoppigCart) {
        this.shoppigCart = shoppigCart;
    }
    public void addNewProduct(Integer id){
        if(shoppigCart.get(id) != null){
            int currentQuantity = shoppigCart.get(id);
            currentQuantity++;
            shoppigCart.put(id,currentQuantity);
        }else{
            shoppigCart.put(id,1);
        }
    }
        public void clearShoppingCart(){
            shoppigCart.clear();
        }

   //hashmap<id qty>
    public void updateProduct(Integer id, Integer qty){
        if(qty==0){
            shoppigCart.remove(id);
        }else{
            shoppigCart.replace(id, qty);
        }
    }
        public void logoff(){
        userId= 0;
        }
}
