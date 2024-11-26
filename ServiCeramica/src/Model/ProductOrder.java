package Model;

public class ProductOrder extends Product {

    private int quantity;
    private double discount;

    public ProductOrder(String id, String name, String category, Double price, int quantity, double discount) {
        super(id, name, category, price);
        this.quantity = quantity;
        this.discount = discount;
    }

    public ProductOrder(Product product, int quantity, double discount){
        super(product.getId(), product.getName(), product.getCategory(), product.getPrice()*quantity);
        this.discount = discount;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getFinalValue(){
        return (getPrice()*quantity)-discount;
    }

    @Override
    public String toString() {
        return "ProductOrder [quantity=" + quantity + ", discount=" + discount + "]";
    }

    
}
