package com.example.adminapp.models;

public class BillDetails {
    private int billId;
    private int productId;
    private int quantity;

    public BillDetails(int billId, int productId, int quantity) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
