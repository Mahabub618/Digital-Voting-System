package com.example.onlinevote;

public class Model {
    private int count;
    private  String imageURL, name, randomUID;

    public Model(){

    }

    public Model(int count, String imageURL, String name, String randomUID) {
        this.count = count;
        this.imageURL = imageURL;
        this.name = name;
        this.randomUID = randomUID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        this.randomUID = randomUID;
    }

}
