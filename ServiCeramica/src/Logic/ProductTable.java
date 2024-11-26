package Logic;


import java.util.List;
import java.util.function.Predicate;

import Model.Product;
import Persistence.FileJsonPersistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductTable {
    private static List<Product> productList;
    private static ObservableList<Product> productListTb;
    private static String PATH = "resources/Products.json";
    private static FileJsonPersistence<Product> productsFile = new FileJsonPersistence<>(PATH);

    public static void loadProductList() {
        productList = productsFile.getObjects(Product.class);
        productListTb = FXCollections.observableArrayList(productList);
    }

    public static ObservableList<Product> getProductListTb() {
        if (productList == null) {
            loadProductList();
        }
        return ProductTable.productListTb;
    }

    public static void reloadProductListTb() {
        if (productList!=null) {
            ProductTable.productListTb = FXCollections.observableArrayList(productList);
        }else{
            loadProductList();
        }
    }

    private static ObservableList<Product> filter(Predicate<Product> predicate) {
        ProductTable.productListTb.removeIf(predicate.negate());
        return ProductTable.productListTb;
    }

    public static ObservableList<Product> searchProductId(String productId){
        return filter(product -> product.getId().startsWith(productId));
    }

    public static ObservableList<Product> searchProductName(String productName){
        return filter(product -> product.getName().startsWith(productName));
    }

    public static ObservableList<Product> filterCategory(String category){
        return filter(product -> product.getCategory().equals(category.toLowerCase()));
    }
}
