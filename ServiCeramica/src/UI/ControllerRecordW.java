package UI;

import java.util.List;

import Logic.FileJsonPersistence;
import Model.CustomerSaleView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ControllerRecordW {
    @FXML
    Button registros;
    @FXML
    Button registrarVenta;
    @FXML
    Button reportes;
    @FXML
    VBox tableBox;
    @FXML
    VBox filterBox;
    @FXML
    VBox content;
    @FXML
    HBox records;

    @FXML
    TableView<CustomerSaleView> tableViewSales;
    @FXML
    private TableColumn<CustomerSaleView, String> idSaleCol;
    @FXML
    private TableColumn<CustomerSaleView, String> custNameCol;
    @FXML
    private TableColumn<CustomerSaleView, String> custCardCol;
    @FXML
    private TableColumn<CustomerSaleView, Double> custPhoneCol;
    @FXML
    private TableColumn<CustomerSaleView, String> saleAmountCol;
    @FXML
    private TableColumn<CustomerSaleView, String> saleDateCol;

    @FXML
    public void initialize() {
        HBox.setHgrow(registros, Priority.ALWAYS);
        HBox.setHgrow(registrarVenta, Priority.ALWAYS);
        HBox.setHgrow(reportes, Priority.ALWAYS);
        HBox.setHgrow(tableBox, Priority.ALWAYS);
        VBox.setVgrow(content, Priority.ALWAYS);
        VBox.setVgrow(records, Priority.ALWAYS);
        tableBox.setMaxWidth(Double.MAX_VALUE);
        registros.setMaxWidth(Double.MAX_VALUE);
        registrarVenta.setMaxWidth(Double.MAX_VALUE);
        reportes.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(Double.MAX_VALUE);
        records.setMaxHeight(Double.MAX_VALUE);

        idSaleCol.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custCardCol.setCellValueFactory(new PropertyValueFactory<>("custCard"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        saleAmountCol.setCellValueFactory(new PropertyValueFactory<>("saleAmount"));
        saleDateCol.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        
        FileJsonPersistence<CustomerSaleView> salesRecord = new FileJsonPersistence<CustomerSaleView>("resources/salesRecord.json");
        List<CustomerSaleView> salesRecordList= salesRecord.getObjects(CustomerSaleView.class);
        ObservableList<CustomerSaleView> customerSales = FXCollections.observableArrayList(
            salesRecordList
        );
        tableViewSales.setItems(customerSales);
    }
}
