package com.example.adminapp.models;

public class User {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String role;
    private String email;
    private String password;

    public User(int id, String name, String phone, String address, String role, String email, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    public User(String name, String phone, String address, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }
    public User(){}

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
