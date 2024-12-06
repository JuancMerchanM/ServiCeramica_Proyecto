package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Model.Customer;
import Model.Sale;
import Model.SaleRecord;
import Persistence.FileJsonPersistence;

public class SaleManage {
    private static String PATH = "resources/sales.json";
    private static List<Sale> saleList;
    private static FileJsonPersistence<Sale> saleFile = new FileJsonPersistence<>(PATH); 
    
    public static void loadSales(){
        saleList = saleFile.getObjects(Sale.class);
    }

    public static void saveSales(){
        saleFile.writeObject(saleList, Sale.class);
    }

    public static Sale getSale(String idSale){
        for (Sale sale : saleList) {
            if (idSale.equals(sale.getSaleId())) {
                return sale;
            }
        }
        return null;
    }

    public static List<SaleRecord> getSalesTb() {
        if (saleList.size()==0) {
            return new ArrayList<>();
        }

        return saleList.stream().map(sale -> {
            Customer customer = sale.getCustomer();
            return new SaleRecord(
                sale.getSaleId(),
                customer.getName(),
                customer.getIdCard(),
                customer.getPhone(),
                sale.getPayment().getAmount(),
                sale.getSaleDate()
            );
        }).collect(Collectors.toList());
    } 

    public static boolean createSale(Sale newSale){
        newSale.setSaleId(String.format("%03d", saleList.size()+1));
        saleList.add(newSale);
        return true;
    }

    public static List<Sale> getList(){
        return saleList;
    }
}
