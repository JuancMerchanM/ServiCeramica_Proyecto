package Logic;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import Model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ManageSaleTable {
    private ObservableList<Sale> salesRecordList;

    public ManageSaleTable() {
        loadSalesRecordList();
    }

    private void loadSalesRecordList() {
        FileJsonPersistence<Sale> salesRecord = new FileJsonPersistence<>("resources/salesRecord.json");
        List<Sale> salesList = salesRecord.getObjects(Sale.class);
        this.salesRecordList = FXCollections.observableArrayList(salesList);
    }

    public ObservableList<Sale> getSalesRecordList() {
        if (salesRecordList == null) {
            loadSalesRecordList();
        }
        return salesRecordList;
    }

    public void reloadSalesRecordList() {
        loadSalesRecordList();
    }

    private ObservableList<Sale> filter(Predicate<Sale> predicate) {
        ObservableList<Sale> salesList = getSalesRecordList();
        salesList.removeIf(predicate.negate());
        return salesList;
    }

    public ObservableList<Sale> searchIdSale(String idSale){
        return filter(sale -> sale.getSaleId().startsWith(idSale));
    }

    public ObservableList<Sale> searchCustCard(String custCard){
        return filter(sale -> sale.getCustCard().startsWith(custCard));
    }
    
    public ObservableList<Sale> searchCustName(String custName){
        return filter(sale -> sale.getCustName().startsWith(custName));
    }

    public ObservableList<Sale> filterDate(LocalDate start, LocalDate end){
        return filter(sale -> sale.getSaleDate().isAfter(start) && sale.getSaleDate().isBefore(end));
    }

    public ObservableList<Sale> filterPrice(Double start, Double end){
        return filter(sale -> sale.getSaleAmount() > start && sale.getSaleAmount() < end);
    }

    public void setSalesRecordList(ObservableList<Sale> salesRecordList){
        this.salesRecordList = salesRecordList;
    }
}
