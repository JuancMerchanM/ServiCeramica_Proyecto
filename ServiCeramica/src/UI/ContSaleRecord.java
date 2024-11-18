package UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Logic.FileJsonPersistence;
import Logic.ProductOrderTable;
import Logic.ProductTable;
import Model.Product;
import Model.ProductOrder;
import Model.SaleRecord;
import Run.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContSaleRecord {
    @FXML private Button registros;
    @FXML private Button registrarVenta;

    @FXML private TextField searchBar;
    @FXML private ComboBox<String> searchOption;

    @FXML private ComboBox<String> searchOptionCat;

    @FXML private TableView<Product> productTb;
    @FXML private TableColumn<Product,String> prodId; 
    @FXML private TableColumn<Product,String> prodName; 
    @FXML private TableColumn<Product,String> prodCategory; 
    @FXML private TableColumn<Product,String> prodPrice; 

    @FXML private TextField infProdId;
    @FXML private TextField infProdName;
    @FXML private TextField infProdCat;
    @FXML private TextField infProdPrice;
    @FXML private TextField inProdDiscount;
    @FXML private TextField inProdQuantity;
    @FXML private Button productMode;

    @FXML private TableView<ProductOrder> selectedProductTb;

    private ProductTable manageProductTable;
    private ProductOrderTable prodOrderTable;

    @FXML
    public void initialize(){
        registrarVenta.setMaxWidth(Double.MAX_VALUE);
        registros.setMaxWidth(Double.MAX_VALUE);

        productTb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        selectedProductTb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        prodId.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        
        manageProductTable = new ProductTable();
        productTb.setItems(manageProductTable.getProductListTb());

        productTb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product oldValue, Product newValue) {
                if (newValue != null) {
                    showProduct(newValue);
                }
            }
        });

        searchOptionCat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterCategory(newValue);
            }
        });

    }

    @FXML
    public void changeRecord() throws IOException{
        App.setRoot("..UI/RecordWindow");
    }

    @FXML
    public void searchProduct(){
        manageProductTable.reloadProductListTb();
        String choiceSearch = searchOption.getValue();
        String textSearch = searchBar.getText();
        ObservableList<Product> productListTb;
        if (textSearch.isEmpty()) {
            return;
        }

        if (choiceSearch.equals("Id producto")) {
            productListTb = manageProductTable.searchProductId(textSearch);
        }else if (choiceSearch.equals("Nombre")) {
            productListTb = manageProductTable.searchProductName(textSearch);
        }else{
            productListTb = manageProductTable.getProductListTb();
        }

        productTb.setItems(productListTb);

    }

    @FXML
    public void productManagement(){
        String mode = productMode.getText();
        if (mode.equals("Agregar")) {
            Product selectedProduct = productTb.getSelectionModel().getSelectedItem();
            Double discount = Double.parseDouble(inProdDiscount.getText());
            int quantity = Integer.parseInt(inProdQuantity.getText());
            prodOrderTable.addProduct(new ProductOrder(selectedProduct, quantity, discount));
        }else{
            
        }
    }

    public void filterCategory(String choiceCategory){
        manageProductTable.reloadProductListTb();
        
        productTb.setItems(
            manageProductTable.filterCategory(choiceCategory)
        );
    }

    public void showProduct(Product product){
        infProdId.setText(product.getId());
        infProdName.setText(product.getName());
        infProdCat.setText(product.getCategory());
        infProdPrice.setText(product.getPrice().toString());
        productMode.setText("Agregar");
    }
}
