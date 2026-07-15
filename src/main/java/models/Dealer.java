package models;

public class Dealer {
    private String dealerCode;
    private String name;
    private String phone;
    private String location;

    public Dealer(String dealerCode, String name, String phone, String location){
        this.dealerCode = dealerCode;
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }




}
