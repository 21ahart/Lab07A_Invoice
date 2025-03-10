import java.util.ArrayList;

public class Invoice {
    private ArrayList<LineItem> lineItems = new ArrayList<>();

    public Invoice(){}

    public void addLineItem(LineItem item){
        lineItems.add(item);
    }

    public void removeLineItem(String productName) {
        lineItems.removeIf(item -> item.getProductName().equals(productName));
    }

    public double computeTotal(){
        double total = 0.0;
        for(LineItem item: lineItems){
            total += item.computeCost();
        }
        return total;
    }

    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }
}
