package com.example.md18_and102_asm.model;

public class Shirt {
    private int idmv;
    private String nameshirt;
    private String describe;
    private String inventory;
    private String avatar;

    public Shirt() {
    }

    public Shirt(int idmv, String nameshirt, String describe, String inventory, String avatar) {
        this.idmv = idmv;
        this.nameshirt = nameshirt;
        this.describe = describe;
        this.inventory = inventory;
        this.avatar = avatar;
    }

    public int getIdmv() {
        return idmv;
    }

    public void setIdmv(int idmv) {
        this.idmv = idmv;
    }

    public String getNameshirt() {
        return nameshirt;
    }

    public void setNameshirt(String nameshirt) {
        this.nameshirt = nameshirt;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
