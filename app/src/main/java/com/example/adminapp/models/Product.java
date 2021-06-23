package com.example.adminapp.models;

import java.util.List;

public class Product {
    private int id;
    private String name;
    private String category;
    private List<FilePath> imgPath;
    private String description;
    private int quantity;
    private int price;

    public Product(int id, String name, String category, List<FilePath> imgPath, String descripton, int quantity, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imgPath = imgPath;
        this.description = descripton;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<FilePath> getImgPath() {
        return imgPath;
    }

    public void setImgPath(List<FilePath> imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescripton() {
        return description;
    }

    public void setDescripton(String descripton) {
        this.description = descripton;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
