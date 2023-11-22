package com.example.test;

public class ModelClass {

    private int image;
    private String carName;
    private String rating;
    private String price;



    public ModelClass(int image, String carName, String rating, String price) {
        this.image = image;
        this.carName = carName;
        this.rating = rating;
        this.price = price;

    }

    public int getImage() {
        return image;
    }

    public String getCarName() {
        return carName;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
