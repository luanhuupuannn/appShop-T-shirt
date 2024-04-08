package com.example.md18_and102_asm.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String name; // Name of the product
    private String size;  // Size of the product (optional)
    private String quantity; // Quantity of the product
    private String price;   // Price of the product

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    // Constructor
    public Product(String name, String size, String quantity, String price) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters (optional)

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(size);
        dest.writeString(quantity);
        dest.writeString(price);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in.readString(), in.readString(), in.readString(), in.readString());
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
