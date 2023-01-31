package com.example.onlinevote;

public class Candidate {
    String partyName;
    String image;
    int Count;

    public Candidate(String partyName, String image, int Count) {
        this.partyName = partyName;
        this.image = image;
        this.Count = Count;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
