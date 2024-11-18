package Model;

public class ProductOrder extends Product {

    private int prodQuantity;
    private double prodDiscount;

    public ProductOrder(String id, String name, String category, Double price, int prodQuantity, double prodDiscount) {
        super(id, name, category, price);
        this.prodQuantity = prodQuantity;
        this.prodDiscount = prodDiscount;
    }

    public ProductOrder(Product product, int prodQuantity, double prodDiscount){
        super(product.getId(), product.getName(), product.getCategory(), product.getPrice()*prodQuantity);
        this.prodDiscount = prodDiscount;
        this.prodQuantity = prodQuantity;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }
    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }
    public double getProdDiscount() {
        return prodDiscount;
    }
    public void setProdDiscount(double prodDiscount) {
        this.prodDiscount = prodDiscount;
    }

}
