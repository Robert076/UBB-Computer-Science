package Model;

public class Sugar implements IEnt {
    private Integer pricePerKg;

    public Sugar(Integer price) {
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

    public String toString() {
        return "Sugar" + this.pricePerKg.toString();
    }
}
