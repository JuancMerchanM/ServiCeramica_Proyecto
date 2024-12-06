package UI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Logic.ProductOrderTable;
import Logic.ProductTable;
import Logic.SaleManage;
import Model.CashPayment;
import Model.Customer;
import Model.Payment;
import Model.Product;
import Model.ProductOrder;
import Model.Sale;
import Model.TransferPayment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ContSaleRecord {
    @FXML
    private Button registros;
    @FXML
    private Button registrarVenta;

    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> searchOption;

    @FXML
    private ComboBox<String> searchOptionCat;

    @FXML
    private TextField custCardIn;
    @FXML
    private TextField custNameIn;
    @FXML
    private TextField custPhoneIn;
    @FXML
    private TextField custAddressIn;

    @FXML
    private TableView<Product> productTb;
    @FXML
    private TableColumn<Product, String> prodId;
    @FXML
    private TableColumn<Product, String> prodName;
    @FXML
    private TableColumn<Product, String> prodCategory;
    @FXML
    private TableColumn<Product, String> prodPrice;

    @FXML
    private TextField saleDateInf;
    @FXML
    private TextField saleTimeInf;
    @FXML
    private TextField infProdId;
    @FXML
    private TextField infProdName;
    @FXML
    private TextField infProdCat;
    @FXML
    private TextField infProdPrice;
    @FXML
    private TextField inProdDiscount;
    @FXML
    private TextField inProdQuantity;
    @FXML
    private Button productMode;
    @FXML
    private Button editOrderProduct;

    @FXML
    private TableView<ProductOrder> selectedProductTb;
    @FXML
    private TableColumn<ProductOrder, String> saleProdId;
    @FXML
    private TableColumn<ProductOrder, String> saleProdName;
    @FXML
    private TableColumn<ProductOrder, String> saleProdCat;
    @FXML
    private TableColumn<ProductOrder, String> saleProdQuan;
    @FXML
    private TableColumn<ProductOrder, String> saleProdPrice;
    @FXML
    private ComboBox<String> opPaymenMethod;

    @FXML
    private TextField saleValue;
    @FXML
    private TextField amountReceivedIn;
    @FXML
    private HBox boxTransferPayment;
    @FXML
    private HBox boxCashPayment;
    @FXML
    private ComboBox<String> opTransferMethod;
    @FXML
    private TextField senderNumberIn;

    @FXML
    public void initialize() {
        configureButtons();
        configureTables();
        setupListeners();
        setupVisibilityBindings();
        setupDateTime();
    }

    private void configureButtons() {
        registrarVenta.setMaxWidth(Double.MAX_VALUE);
        registros.setMaxWidth(Double.MAX_VALUE);
    }

    private void configureTables() {
        // Configuración de políticas de redimensionamiento
        productTb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        selectedProductTb.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Configuración de columnas para productTb
        prodId.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Asignar datos a la tabla
        productTb.setItems(ProductTable.getProductListTb());

        // Configuración de columnas para selectedProductTb
        saleProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        saleProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        saleProdCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        saleProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        saleProdQuan.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void setupListeners() {
        // Listener para la selección en productTb
        productTb.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                showProduct(newValue);
            }
        });

        // Listener para la selección en selectedProductTb
        selectedProductTb.getSelectionModel().selectedItemProperty()
                .addListener((_, _, newValue) -> {
                    if (newValue != null) {
                        showProductOrder(newValue);
                    }
                });

        // Listener para el filtro de categorías
        searchOptionCat.valueProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                filterCategory(newValue);
            }
        });
    }

    private void setupVisibilityBindings() {
        // Listener para el método de pago
        opPaymenMethod.valueProperty().addListener((_, _, newValue) -> {
            if ("Efectivo".equals(newValue)) {
                setVisibleNode(boxCashPayment, true);
                setVisibleNode(boxTransferPayment, false);
                senderNumberIn.setText(custPhoneIn.getText());
            } else {
                setVisibleNode(boxCashPayment, false);
                setVisibleNode(boxTransferPayment, true);
            }
        });
    }

    private void setupDateTime(){
        saleDateInf.setText(LocalDate.now().toString());
        configureTimeField(saleTimeInf);
    }

    private void configureTimeField(TextField field){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Runnable updateTime = () -> field.setText(LocalTime.now().format(formatter));
        updateTime.run();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(60), _ -> updateTime.run())
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repetir indefinidamente
        timeline.play();
    }

    private void setVisibleNode(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    @FXML
    public void registerSale() {
        Sale newSale = createSaleFromInputs();
        if (newSale == null) {
            return;
        }
        double turned = parseDoubleSafe(amountReceivedIn.getText(),"Monto recibido") - ProductOrderTable.getTotalAmount();
        if (turned<0) {
            Alerts.failedAmountReceived();
            return;
        }
        Alerts.succesRegisterSale(newSale, turned);
        SaleManage.createSale(newSale);
        cleanFields();
    }

    private Sale createSaleFromInputs() {
        String custCard = custCardIn.getText();
        if (contieneLet_EsVacio(custCard) || custCard.length()<10) {
            Alerts.errorParsingNumber("Cedula cliente");
            return null;
        }
        String custName = custNameIn.getText();
        if (contieneNum_EsVacio(custName)) {
            Alerts.errorParsingString("Nombre cliente");
            return null;
        }
        String custPhone = custPhoneIn.getText();
        if (contieneLet_EsVacio(custPhone) || custCard.length()<10) {
            Alerts.errorParsingNumber("Telefono cliente");
            return null;
        }
        String custAddress = custAddressIn.getText();
        if (custAddress.trim().isEmpty()) {
            Alerts.errorParsingString("Direccion cliente");
            return null;
        }
        List<ProductOrder> listProductOrder = ProductOrderTable.getList();
        if (listProductOrder.isEmpty()) {
            Alerts.errorListProducts();
            return null;
        }
        LocalDate saleDate = LocalDate.parse(saleDateInf.getText());
        String saleTime = saleTimeInf.getText();
        Payment payment = getPayment();

        return new Sale(
            saleTime, 
            new Customer(custName, custCard, custPhone, custAddress), 
            saleDate, 
            saleTime, 
            payment, 
            listProductOrder);
    }

    private Payment getPayment(){
        double totalAmount = ProductOrderTable.getTotalAmount();
        if (opPaymenMethod.getValue().equals("Efectivo")) {
            double cashReceived =  parseDoubleSafe(amountReceivedIn.getText(), "Monto recibido");
            return new CashPayment(totalAmount, cashReceived);
        }else{
            String senderNumber = senderNumberIn.getText();
            String platform = opTransferMethod.getValue();
            return new TransferPayment(totalAmount, senderNumber, platform);
        }
    }

    @FXML
    public void cleanFields() {
        cleanFieldsProduct();
        cleanFieldsCust();
        selectedProductTb.setItems(null);
        saleValue.setText("");
    }

    @FXML
    public void changeRecord() throws IOException {
        ViewManager.changeRecord();
    }

    @FXML
    public void searchProduct() {
        if (searchBar.getText().isEmpty()){
            ProductTable.reloadProductListTb();
            return; // Salir si no hay texto en el buscador
        }
        String choiceSearch = searchOption.getValue();
        String textSearch = searchBar.getText();

        ObservableList<Product> productListTb = switch (choiceSearch) {
            case "Id producto" -> ProductTable.searchProductId(textSearch);
            case "Nombre" -> ProductTable.searchProductName(textSearch);
            default -> ProductTable.getProductListTb();
        };

        productTb.setItems(productListTb);
    }

    @FXML
    public void productManagement() {
        String mode = productMode.getText();

        if (mode.equals("Agregar")) {
            addSelectedProductToOrder();
        } else {
            removeSelectedProductFromOrder();
        }
    }

    @FXML
    public void editProduct(){
        ProductOrder productEditing = selectedProductTb.getSelectionModel().getSelectedItem();
        int newQuantity = parseIntSafe(inProdQuantity.getText());
        double newDiscount = parseDoubleSafe(inProdDiscount.getText(), "Descuento");
        ProductOrderTable.editProduct(productEditing, newQuantity, newDiscount);
        updateSaleValue();
        selectedProductTb.refresh();
    }

    private void addSelectedProductToOrder() {
        Product selectedProduct = productTb.getSelectionModel().getSelectedItem();
        if (selectedProduct == null)
            return; // Salir si no hay un producto seleccionado

        Double discount = parseDoubleSafe(inProdDiscount.getText(),"Descuento");
        int quantity = parseIntSafe(inProdQuantity.getText());
        ProductOrderTable.addProduct(new ProductOrder(selectedProduct, quantity, discount));
        updateSaleValue();
        updateSelectedProductTable();
    }

    private void removeSelectedProductFromOrder() {
        ProductOrder selectedProductOrder = selectedProductTb.getSelectionModel().getSelectedItem();
        if (selectedProductOrder == null)
            return; // Salir si no hay un pedido seleccionado

        ProductOrderTable.removeProduct(selectedProductOrder.getId());
        updateSelectedProductTable();
        selectedProductTb.refresh();
        updateSaleValue();
        cleanFieldsProduct();
    }

    private void updateSelectedProductTable() {
        selectedProductTb.setItems(ProductOrderTable.getListTable());
    }

    private void updateSaleValue(){
        saleValue.setText(String.format("%.2f", ProductOrderTable.getTotalAmount()));
    }

    private Double parseDoubleSafe(String text, String field) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            Alerts.errorParsingNumber(text);
            return 0.0; // Valor predeterminado si hay un error
        }
    }

    private int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 1; // Valor predeterminado si hay un error
        }
    }

    public void filterCategory(String choiceCategory) {
        ProductTable.reloadProductListTb();
        productTb.setItems(ProductTable.filterCategory(choiceCategory));
    }

    public void showProduct(Product product) {
        if (product == null)
            return; // Salir si no hay un producto

        infProdId.setText(product.getId());
        infProdName.setText(product.getName());
        infProdCat.setText(product.getCategory());
        infProdPrice.setText(product.getPrice().toString());
        resetProductInputFields();
        setVisibleNode(editOrderProduct, false);
    }

    private void resetProductInputFields() {
        inProdDiscount.setText("0");
        inProdQuantity.setText("1");
        productMode.setText("Agregar");
    }

    public void showProductOrder(ProductOrder productOrder) {
        if (productOrder == null) return; // Verifica que no sea nulo para evitar errores
    
        setTextField(infProdId, productOrder.getId());
        setTextField(infProdName, productOrder.getName());
        setTextField(infProdCat, productOrder.getCategory());
        setTextField(infProdPrice, productOrder.getPrice().toString());
        setTextField(inProdDiscount, String.valueOf(productOrder.getDiscount()));
        setTextField(inProdQuantity, String.valueOf(productOrder.getQuantity())); // Corrección: se usa quantity, no price
        productMode.setText("Remover");
        setVisibleNode(editOrderProduct, true);
    }
    
    private void setTextField(TextField field, String value) {
        if (value != null) {
            field.setText(value);
        } else {
            field.clear(); // Limpia si el valor es nulo
        }
    }

    public void cleanFieldsProduct() {
        clearTextFields(infProdId, infProdName, infProdCat, infProdPrice, inProdDiscount, inProdQuantity);
    }

    public void cleanFieldsCust() {
        clearTextFields(custNameIn, custCardIn, custAddressIn, custPhoneIn);
    }
    
    private void clearTextFields(TextField... fields) {
        for (TextField field : fields) {
            if (field != null) {
                field.clear();
            }
        }
    }

    @FXML
    public void backLogin() throws IOException {
        ViewManager.backLogin();
    }

    private boolean contieneNum_EsVacio(String sstring) {
		if (sstring.trim().isEmpty()) {
			return true;
		}
		for (char l : sstring.toCharArray()) {
			if (Character.isDigit(l)) {
				return true;
			}
		}
		return false;
	}

    private boolean contieneLet_EsVacio(String sstring) {
		sstring = sstring.replaceAll("\\s", "");
		if (sstring.trim().isEmpty()) {
			return true;
		}
		for (char l : sstring.toCharArray()) {
			if (Character.isAlphabetic(l)) {
				return true;
			}
		}
		return false;
	}
}
