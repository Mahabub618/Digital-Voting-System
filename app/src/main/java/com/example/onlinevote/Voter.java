package com.example.onlinevote;

public class Voter {
    private String Name, ImageURL, RandomUID;
    int Count;

    public Voter()
    {

    }
    public Voter(String name, String imageURL, String randomUID, int count)
    {
        Name = name;
        ImageURL = imageURL;
        RandomUID = randomUID;
        Count = count;

    }

    public int getCount() { return Count; }

    public void setCount(int count) { Count = count; }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public Voter(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
