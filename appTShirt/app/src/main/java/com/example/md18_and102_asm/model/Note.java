package com.example.md18_and102_asm.model;

public class Note {
    private String namemovietry;
    private String time;
    private String idchair;
    private int quantity;
    private int price;

    public Note() {
    }

    public Note(String namemovietry, String time, String idchair, int quantity, int price) {
        this.namemovietry = namemovietry;
        this.time = time;
        this.idchair = idchair;
        this.quantity = quantity;
        this.price = price;
    }

    public String getNamemovietry() {
        return namemovietry;
    }

    public void setNamemovietry(String namemovietry) {
        this.namemovietry = namemovietry;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdchair() {
        return idchair;
    }

    public void setIdchair(String idchair) {
        this.idchair = idchair;
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
