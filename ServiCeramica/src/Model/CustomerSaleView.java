package Model;

import java.time.LocalDate;

public class CustomerSaleView {
    private String saleId;
    private String custName;
    private String custCard;
    private String custPhone;
    private double saleAmount;
    private LocalDate saleDate;

    public CustomerSaleView(String saleId, String custName, String custCard, String custPhone, double saleAmount,
            LocalDate saleDate) {
        this.saleId = saleId;
        this.custName = custName;
        this.custCard = custCard;
        this.custPhone = custPhone;
        this.saleAmount = saleAmount;
        this.saleDate = saleDate;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCard() {
        return custCard;
    }

    public void setCustCard(String custCard) {
        this.custCard = custCard;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

}
