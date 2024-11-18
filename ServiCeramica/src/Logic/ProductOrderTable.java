package Logic;

import java.util.HashMap;

import Model.ProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductOrderTable {
    private HashMap<String,ProductOrder> productList;

    public ProductOrderTable(){
        this.productList = new HashMap<>();
    }

    public void removeProduct(String idProduct){
        productList.remove(idProduct);
    }

    public void addProduct(ProductOrder productOrder){
        if (productList.containsKey(productOrder.getId())) {
            return;
        }
        productList.put(productOrder.getId(), productOrder);
    }

    public ObservableList<ProductOrder> getListTable(){
        return FXCollections.observableArrayList(productList.values());
    }
}