package com.example.adminapp.models;

import java.util.Date;

public class Bill {
    private int id;
    private Date date;
    private int userId;
    private int totalPrice;
    private int state;
    private boolean isPay;

    public Bill(int id, Date date, int userId, int totalPrice, int state, boolean isPay) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.state = state;
        this.isPay = isPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }
}
