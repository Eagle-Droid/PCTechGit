package onlineShop.PCTech.Database;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String photoFile1;
    private String photoFile2;
    private String photoFile3;
    private int foreignKey;

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotoFile1() {
        return photoFile1;
    }

    public void setPhotoFile1(String photoFile1) {
        this.photoFile1 = photoFile1;
    }

    public String getPhotoFile2() {
        return photoFile2;
    }

    public void setPhotoFile2(String photoFile2) {
        this.photoFile2 = photoFile2;
    }

    public String getPhotoFile3() {
        return photoFile3;
    }

    public void setPhotoFile3(String photoFile3) {
        this.photoFile3 = photoFile3;
    }
}
