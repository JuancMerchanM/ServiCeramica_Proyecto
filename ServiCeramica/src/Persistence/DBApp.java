package Persistence;

import Logic.ProductTable;
import Logic.SaleManage;

public class DBApp {

    public static void loadDataApp(){
        SaleManage.loadSales();
        ProductTable.loadProductList();
    }

    public static void saveDataApp(){
        SaleManage.saveSales();
    }
}
