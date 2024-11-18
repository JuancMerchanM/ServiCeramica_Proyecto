package Logic;


import java.util.List;
import java.util.function.Predicate;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductTable {
    private List<Product> productList;
    private ObservableList<Product> productListTb;
    private String PATH = "resources/Products.json";

    public ProductTable() {
        loadProductList();
    }

    private void loadProductList() {
        FileJsonPersistence<Product> productsFile = new FileJsonPersistence<>(PATH);
        this.productList = productsFile.getObjects(Product.class);
        this.productListTb = FXCollections.observableArrayList(productList);
    }

    public ObservableList<Product> getProductListTb() {
        if (productList == null) {
            loadProductList();
        }
        return productListTb;
    }

    public void reloadProductListTb() {
        if (productList!=null) {
            this.productListTb = FXCollections.observableArrayList(productList);
        }else{
            loadProductList();
        }
    }

     private ObservableList<Product> filter(Predicate<Product> predicate) {
        productListTb.removeIf(predicate.negate());
        return productListTb;
    }

    public ObservableList<Product> searchProductId(String productId){
        return filter(product -> product.getId().startsWith(productId));
    }

    public ObservableList<Product> searchProductName(String productName){
        return filter(product -> product.getName().startsWith(productName));
    }

    public ObservableList<Product> filterCategory(String category){
        return filter(product -> product.getCategory().equals(category.toLowerCase()));
    }
}
