package onlineShop.PCTech.Controller;

import onlineShop.PCTech.Database.Product;

public class CartProduct extends Product {
    private int quantity;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
