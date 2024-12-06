package Model;

import java.io.Serializable;

public abstract class Payment implements Serializable{
    private double amount; 
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payment(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public abstract String getPaymentDetails();
}
