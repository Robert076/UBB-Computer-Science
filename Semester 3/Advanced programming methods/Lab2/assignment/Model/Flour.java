package Model;

public class Flour implements IEnt {
    private Integer pricePerKg;

    public Flour(Integer price) {
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
}
