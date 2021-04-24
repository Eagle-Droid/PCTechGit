package onlineShop.PCTech.Database;

public class Order {
    private int id;
    private int iserId;
    private String address;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIserId() {
        return iserId;
    }

    public void setIserId(int iserId) {
        this.iserId = iserId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
