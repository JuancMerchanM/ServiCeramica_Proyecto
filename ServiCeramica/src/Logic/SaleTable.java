package Logic;

import java.time.LocalDate;
import java.util.function.Predicate;

import Model.SaleRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SaleTable {
    private static ObservableList<SaleRecord> salesRecordTb;

    private void loadSalesRecordList() {
        SaleTable.salesRecordTb = FXCollections.observableArrayList(SaleManage.getSalesTb());
    }

    public ObservableList<SaleRecord> getSalesRecordList() {
        if (salesRecordTb == null) {
            loadSalesRecordList();
        }
        return salesRecordTb;
    }

    public void reloadSalesRecordList() {
        if (salesRecordTb!=null) {
            SaleTable.salesRecordTb = FXCollections.observableArrayList(SaleManage.getSalesTb());            
        }else{
            loadSalesRecordList();
        }
    }

    private ObservableList<SaleRecord> filter(Predicate<SaleRecord> predicate) {
        salesRecordTb.removeIf(predicate.negate());
        return salesRecordTb;
    }

    public ObservableList<SaleRecord> searchIdSale(String idSale){
        return filter(sale -> sale.getSaleId().startsWith(idSale));
    }

    public ObservableList<SaleRecord> searchCustCard(String custCard){
        return filter(sale -> sale.getCustCard().startsWith(custCard));
    }
    
    public ObservableList<SaleRecord> searchCustName(String custName){
        return filter(sale -> sale.getCustName().startsWith(custName));
    }

    public ObservableList<SaleRecord> filterDate(LocalDate start, LocalDate end){
        return filter(sale -> !(sale.getSaleDate().isBefore(start) || sale.getSaleDate().isAfter(end)));
    }

    public ObservableList<SaleRecord> filterPrice(Double start, Double end){
        return filter(sale -> sale.getSaleAmount() >= start && sale.getSaleAmount() <= end);
    }

    public void setSalesRecordList(ObservableList<SaleRecord> salesRecordTb){
        SaleTable.salesRecordTb = salesRecordTb;
    }
}
