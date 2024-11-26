package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Sale implements Serializable{
    private String saleId;
    private Customer customer;
    private LocalDate saleDate;
    private String saleTime;
    private double totalAmount;
    private List<ProductOrder> products;

    public Sale(String saleId, Customer customer, LocalDate saleDate, String saleTime, double totalAmount,
            List<ProductOrder> products) {
        this.saleId = saleId;
        this.customer = customer;
        this.saleDate = saleDate;
        this.saleTime = saleTime;
        this.totalAmount = totalAmount;
        this.products = products;
    }



    public String getSaleId() {
        return saleId;
    }
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public LocalDate getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }
    public String getSaleTime() {
        return saleTime;
    }
    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<ProductOrder> getProducts() {
        return products;
    }
    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

}
