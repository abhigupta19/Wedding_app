package com.example.wedding;

public class User {
    private String phone;
    private String name;

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

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getTkt_id() {
        return tkt_id;
    }

    public void setTkt_id(int tkt_id) {
        this.tkt_id = tkt_id;
    }

    private int card_id;
    private int tkt_id;

}
