package com.byteMe.models;

import java.util.regex.Pattern;

public abstract class User {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String address;
    private String gender;

    public User(String email, String password, String name, String phoneNumber, String address, String gender,boolean b) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        this.email = email;
        this.password = password;
        this.name = name != null ? name : "Unknown";
        this.phoneNumber = phoneNumber != null ? phoneNumber : "Unknown";
        this.address = address != null ? address : "Unknown";
        this.gender = gender != null ? gender : "Unspecified";
    }

    //getters and setters
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getName() {return name;}
    public void setName(String name) {if (name != null && !name.isEmpty()) {this.name = name;}}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {if (phoneNumber != null && !phoneNumber.isEmpty()) {this.phoneNumber = phoneNumber;}}
    public String getAddress() {return address;}

    public void setAddress(String address) {
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }
    }
    public String getGender() {return gender;}

    public void setGender(String gender) {
        if (gender != null && !gender.isEmpty()) {
            this.gender = gender;
        }
    }
    public abstract void showMenu();

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
