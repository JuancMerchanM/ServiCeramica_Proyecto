package Model;

import java.io.Serializable;

public class Customer implements Serializable{
    private String name;
    private String idCard;
    private String phone;
    private String address;

    public Customer(String name, String idCard, String phone, String address) {
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
        this.address = address;
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
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
