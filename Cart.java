package eMEDi;

public class Cart {
    String name;
    int quantity;
    double price;
    String manufacturer;

    public Cart(String name, int quantity, double price, String manufacturer) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

}
