package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.ProductOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductOrderTable {
    private static HashMap<String,ProductOrder> productList =  new HashMap<>();
    private static double TotalAmount = 0;

    public static void removeProduct(String idProduct){
        productList.remove(idProduct);
    }

    public static void addProduct(ProductOrder productOrder){
        if (productList.containsKey(productOrder.getId())) {
            return;
        }
        productList.put(productOrder.getId(), productOrder);
        ProductOrderTable.TotalAmount += productOrder.getFinalValue();
    }

    public static void editProduct(ProductOrder productOrder, int newQuantity, double newDiscount){
        ProductOrderTable.TotalAmount -= productOrder.getFinalValue();
        productOrder.setDiscount(newDiscount);
        productOrder.setQuantity(newQuantity);
        ProductOrderTable.TotalAmount+=productOrder.getFinalValue();
        ProductOrderTable.productList.put(productOrder.getId(), productOrder);
    }

    public static ObservableList<ProductOrder> getListTable(){
        return FXCollections.observableArrayList(productList.values());
    }

    public static List<ProductOrder> getList(){
        return new ArrayList<>(productList.values());
    }

    public static double getTotalAmount(){
        return ProductOrderTable.TotalAmount;
    }

}