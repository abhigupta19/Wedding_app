package com.example.wedding;

public class User {
    private String phone;
    private String name;
    private String card_id;
    private String tkt_id;
    private String pnr;

    public User(String phone, String name, String card_id, String tkt_id,String pnr) {
        this.phone = phone;
        this.name = name;
        this.card_id = card_id;
        this.tkt_id = tkt_id;
        this.pnr=pnr;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getTkt_id() {
        return tkt_id;
    }

    public void setTkt_id(String tkt_id) {
        this.tkt_id = tkt_id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
