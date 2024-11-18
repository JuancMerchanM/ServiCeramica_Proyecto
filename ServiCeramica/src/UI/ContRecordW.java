package UI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import Logic.DatePickerAdapter;
import Logic.FileJsonPersistence;
import Logic.SaleTable;
import Model.SaleRecord;
import Run.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ContRecordW {
    @FXML private Button registros;
    @FXML private Button registrarVenta;
    @FXML private VBox infoSale;

    @FXML private TableView<SaleRecord> tableViewSales;
    @FXML private TableColumn<SaleRecord, String> idSaleCol;
    @FXML private TableColumn<SaleRecord, String> custNameCol;
    @FXML private TableColumn<SaleRecord, String> custCardCol;
    @FXML private TableColumn<SaleRecord, Double> custPhoneCol;
    @FXML private TableColumn<SaleRecord, String> saleAmountCol;
    @FXML private TableColumn<SaleRecord, String> saleDateCol;

    @FXML private TextField seachBar;
    @FXML private ComboBox<String> searchOption;

    @FXML private DatePicker filDateInit;
    @FXML private DatePicker filDateEnd;
    @FXML private TextField filPriceInit;
    @FXML private TextField filPriceEnd;

    @FXML private Label messageSaleInfo;

    @FXML private Label saleId;
    @FXML private Label saleDate;
    @FXML private Label custName;
    @FXML private Label custContact;
    @FXML private Label amountTotal;
    

    private SaleTable manageSaleTable;

    @FXML
    public void initialize() {
        HBox.setHgrow(registros, Priority.ALWAYS);
        HBox.setHgrow(registrarVenta, Priority.ALWAYS);
        registros.setMaxWidth(Double.MAX_VALUE);
        registrarVenta.setMaxWidth(Double.MAX_VALUE);
        
        idSaleCol.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custCardCol.setCellValueFactory(new PropertyValueFactory<>("custCard"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        saleAmountCol.setCellValueFactory(new PropertyValueFactory<>("saleAmount"));
        saleDateCol.setCellValueFactory(new PropertyValueFactory<>("saleDate"));

        // tableViewSales.widthProperty().addListener((obs, oldWidth, newWidth) -> {
        //     double newColumnWidth = newWidth.doubleValue() / 6; // Dividir el ancho en partes iguales
        //     tableViewSales.getColumns().forEach(column -> column.setPrefWidth(newColumnWidth));
        // });

        tableViewSales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        FileJsonPersistence<SaleRecord> salesRecord = new FileJsonPersistence<SaleRecord>("resources/salesRecord.json");
        List<SaleRecord> salesRecordList = salesRecord.getObjects(SaleRecord.class);
        ObservableList<SaleRecord> customerSales = FXCollections.observableArrayList(
                salesRecordList);
        tableViewSales.setItems(customerSales);

        manageSaleTable = new SaleTable();

        DatePickerAdapter.configureDatePicker(filDateEnd);
        DatePickerAdapter.configureDatePicker(filDateInit);

        tableViewSales.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleRecord>() {

            @Override
            public void changed(ObservableValue<? extends SaleRecord> observableValue, SaleRecord oldValue, SaleRecord newValue) {
                if (newValue != null) {
                    showSale(newValue);
                }
            }
            
        });

    }

    @FXML
    public void backLogin() throws IOException{
        App.setRoot("../UI/InitialWindow");
    }

    @FXML
    public void changeSaleRecord() throws IOException{
        App.setRoot("../UI/SaleRecordW");
    }

    @FXML
    public void searchProduct() {
        String option = searchOption.getValue();
        String searchValue = seachBar.getText();
        manageSaleTable.reloadSalesRecordList();
        ObservableList<SaleRecord> saleList;
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
        manageSaleTable.reloadSalesRecordList();
        ObservableList<SaleRecord> saleFilterList = manageSaleTable.getSalesRecordList();

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

    @FXML
    public void cleanFilters(){
        filDateEnd.setValue(null);
        filDateInit.setValue(null);
        filPriceEnd.setText("");
        filPriceInit.setText("");
        manageSaleTable.reloadSalesRecordList();
        tableViewSales.setItems(manageSaleTable.getSalesRecordList());
    }

    public void showSale(SaleRecord selectSale){
        messageSaleInfo.setManaged(false);
        messageSaleInfo.setVisible(false);
        infoSale.setManaged(true);
        infoSale.setVisible(true);

        saleId.setText(selectSale.getSaleId());
        saleDate.setText(selectSale.getSaleDate().toString());
        custName.setText(selectSale.getCustName());
        custContact.setText(selectSale.getCustPhone());
        amountTotal.setText("Calcular");
    }
}
