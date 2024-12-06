package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Sale implements Serializable {
    private String saleId;
    private Customer customer;
    private LocalDate saleDate;
    private String saleTime;
    private List<ProductOrder> products;
    private Payment payment;

    public Sale(String saleId, Customer customer, LocalDate saleDate,String saleTime, Payment payment,
            List<ProductOrder> products) {
        this.saleId = saleId;
        this.customer = customer;
        this.saleDate = saleDate;
        this.saleTime = saleTime;
        this.products = products;
        this.payment = payment;
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
    public List<ProductOrder> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrder> products) {
        this.products = products;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String showListProducts(){
        String productsString = "";
        for (ProductOrder productOrder : this.products) {
            productsString+= productOrder.toString() + "\n";
        }
        return productsString;
    }

}
