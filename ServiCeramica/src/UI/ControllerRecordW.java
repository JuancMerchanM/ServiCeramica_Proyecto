package UI;

import java.util.List;

import Logic.FileJsonPersistence;
import Model.Sale;
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
    TableView<Sale> tableViewSales;
    @FXML
    private TableColumn<Sale, String> idSaleCol;
    @FXML
    private TableColumn<Sale, String> custNameCol;
    @FXML
    private TableColumn<Sale, String> custCardCol;
    @FXML
    private TableColumn<Sale, Double> custPhoneCol;
    @FXML
    private TableColumn<Sale, String> saleAmountCol;
    @FXML
    private TableColumn<Sale, String> saleDateCol;

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
        
        FileJsonPersistence<Sale> salesRecord = new FileJsonPersistence<Sale>("resources/salesRecord.json");
        List<Sale> salesRecordList= salesRecord.getObjects(Sale.class);
        ObservableList<Sale> customerSales = FXCollections.observableArrayList(
            salesRecordList
        );
        tableViewSales.setItems(customerSales);
    }
}
