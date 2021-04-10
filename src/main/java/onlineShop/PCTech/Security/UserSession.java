package onlineShop.PCTech.Security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
