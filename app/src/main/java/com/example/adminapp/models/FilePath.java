package com.example.adminapp.models;

public class FilePath {
    private int id;
    private String path;
    private int productId;

    public FilePath(int id, String path, int productId) {
        this.id = id;
        this.path = path;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
