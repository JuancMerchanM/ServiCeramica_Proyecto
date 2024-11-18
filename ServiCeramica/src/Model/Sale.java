package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Sale {
    private String saleId;
    private Customer customer; 
    private LocalDate saleDate;
    private LocalTime saleTime;
    private double totalAmount;
    private List<ProductOrder> products;

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
    public LocalTime getSaleTime() {
        return saleTime;
    }
    public void setSaleTime(LocalTime saleTime) {
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
