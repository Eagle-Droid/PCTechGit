package onlineShop.PCTech.Database;

public class Specification {
    private int id;
    private String firstCol;
    private String secondCol;
    private int foreignKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(String firstCol) {
        this.firstCol = firstCol;
    }

    public String getSecondCol() {
        return secondCol;
    }

    public void setSecondCol(String secondCol) {
        this.secondCol = secondCol;
    }

    public int getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(int foreignKey) {
        this.foreignKey = foreignKey;
    }
}
