package Model;

import java.io.Serializable;

public class Customer implements Serializable{
    private String name;
    private String idCard;
    private String phone;

    public Customer(String name, String idCard, String phone) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}