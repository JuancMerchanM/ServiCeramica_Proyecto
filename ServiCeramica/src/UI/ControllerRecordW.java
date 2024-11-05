package UI;

import java.time.LocalDate;
import java.util.List;

import Logic.DatePickerAdapter;
import Logic.FileJsonPersistence;
import Logic.ManageSaleTable;
import Model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField seachBar;
    @FXML
    private ComboBox<String> searchOption;

    @FXML
    DatePicker filDateInit;
    @FXML
    DatePicker filDateEnd;
    @FXML
    TextField filPriceInit;
    @FXML
    TextField filPriceEnd;

    ManageSaleTable manageSaleTable;

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
        List<Sale> salesRecordList = salesRecord.getObjects(Sale.class);
        ObservableList<Sale> customerSales = FXCollections.observableArrayList(
                salesRecordList);
        tableViewSales.setItems(customerSales);

        manageSaleTable = new ManageSaleTable();

        DatePickerAdapter.configureDatePicker(filDateEnd);
        DatePickerAdapter.configureDatePicker(filDateInit);
    }

    @FXML
    public void searchProduct() {
        String option = searchOption.getValue();
        String searchValue = seachBar.getText();
        manageSaleTable.reloadSalesRecordList();
        ObservableList<Sale> saleList;
        if (option.equals("Id Venta")) {
            saleList = manageSaleTable.searchIdSale(searchValue);
        } else if (option.equals("Cedula cliente")) {
            saleList = manageSaleTable.searchCustCard(searchValue);
        } else {
            saleList = manageSaleTable.searchCustName(searchValue);
        }
        tableViewSales.setItems(saleList);
    }

    @FXML
    public void filter() {

        ObservableList<Sale> saleFilterList = manageSaleTable.getSalesRecordList();

        try {

            LocalDate startDate = filDateInit.getValue();
            LocalDate endDate = filDateEnd.getValue();

            if (startDate != null && endDate != null) {
                saleFilterList = manageSaleTable.filterDate(startDate, endDate);
                manageSaleTable.setSalesRecordList(saleFilterList);
            }

            String startPriceText = filPriceInit.getText();
            String endPriceText = filPriceEnd.getText();

            if (!startPriceText.isEmpty() && !endPriceText.isEmpty()) {
                Double startPrice = Double.parseDouble(startPriceText);
                Double endPrice = Double.parseDouble(endPriceText);

                saleFilterList = manageSaleTable.filterPrice(startPrice, endPrice);
            }

            tableViewSales.setItems(saleFilterList);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Precio invalido");
            alert.setContentText("El precio debe ser un numero válido.");
            alert.showAndWait();
        }
    }

}
