package Model;

public class Salt implements IEnt {
    private Integer pricePerKg;

    public Salt(Integer price) {
        this.pricePerKg = price;
    }

    Integer getPrice() {
        return this.pricePerKg;
    }

    void setPrice(Integer newPrice) {
        this.pricePerKg = newPrice;
    }

    public Integer Compute() {
        return this.pricePerKg;
    }

    public String getType() {
        return "Salt";
    }
}
