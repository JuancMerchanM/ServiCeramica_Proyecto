package UI;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import Logic.DatePickerAdapter;
import Logic.Reports;
import Logic.SaleManage;
import Logic.SaleTable;
import Model.Sale;
import Model.SaleRecord;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

public class ContRecordW {
    @FXML private Button registros;
    @FXML private Button registrarVenta;
    @FXML private Button bttnSesion;
    @FXML private VBox infoSale;
    @FXML private VBox boxUserInf;

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

    @FXML private TextField reportTotal;
    @FXML private TextField reportQuantity;
    @FXML private TextField reportBestCat;
    @FXML private Label listProductsString;
    

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

        tableViewSales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        
        manageSaleTable = new SaleTable();

        tableViewSales.setItems(FXCollections.observableArrayList(SaleManage.getSalesTb()));


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
        // App.setRoot("../UI/InitialWindow");
        ViewManager.backLogin();
    }

    @FXML
    public void changeSaleRecord() throws IOException{
        // App.setRoot("../UI/SaleRecordW");
        ViewManager.changeSaleW();
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

        Sale sale = SaleManage.getSale(selectSale.getSaleId());

        saleId.setText(selectSale.getSaleId());
        saleDate.setText(selectSale.getSaleDate().toString());
        custName.setText(selectSale.getCustName());
        custContact.setText(selectSale.getCustPhone());
        amountTotal.setText(String.valueOf(sale.getPayment().getAmount()));
        listProductsString.setText(sale.showListProducts());
    }

    @FXML
    public void displayInfSesion(){
        double xBoxUserInf = bttnSesion.getLayoutX();
        double yBoxUserInf = bttnSesion.getLayoutY() - 65;
        boxUserInf.setVisible(true);
        boxUserInf.setManaged(true);
        Popup popup = new Popup();
        boxUserInf.setLayoutX(xBoxUserInf);
        boxUserInf.setLayoutY(yBoxUserInf);
        popup.getContent().add(boxUserInf);
        ViewManager.showPopup(popup);
    }

    @FXML
    public void generateReport(){
        Map<String, Object> report = Reports.generateReport(SaleManage.getList());
        reportTotal.setText(report.get("TotalSold").toString());
        reportQuantity.setText(report.get("ProductQuantities").toString());
        reportBestCat.setText(report.get("BestCategory").toString());
    }
}
