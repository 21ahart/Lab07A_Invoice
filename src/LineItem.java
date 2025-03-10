public class LineItem {
    private Product item;
    private int amount;

    public LineItem(Product item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public double computeCost(){
        return item.getPrice() * amount;
    }

    public String getProductName(){
        return item.getName();
    }

    public double getProductCost(){
        return item.getPrice();
    }

    public int getAmount(){
        return amount;
    }
    
}
