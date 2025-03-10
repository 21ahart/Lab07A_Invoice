public class Product {
    private double price;
    private String name;

    public Product(double price, String name){
        this.price = price;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }
}
