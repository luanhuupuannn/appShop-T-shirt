package com.example.md18_and102_asm.model;

public class Cart {

    private String namecart;
    private String sizecart;
    private String quantitycart;
    private String pricecart;
    private String username;

    public Cart() {
    }

    public Cart(String namecart, String sizecart, String quantitycart, String pricecart, String username) {
        this.namecart = namecart;
        this.sizecart = sizecart;
        this.quantitycart = quantitycart;
        this.pricecart = pricecart;
        this.username = username;
    }

    public String getNamecart() {
        return namecart;
    }

    public void setNamecart(String namecart) {
        this.namecart = namecart;
    }

    public String getSizecart() {
        return sizecart;
    }

    public void setSizecart(String sizecart) {
        this.sizecart = sizecart;
    }

    public String getQuantitycart() {
        return quantitycart;
    }

    public void setQuantitycart(String quantitycart) {
        this.quantitycart = quantitycart;
    }

    public String getPricecart() {
        return pricecart;
    }

    public void setPricecart(String pricecart) {
        this.pricecart = pricecart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
