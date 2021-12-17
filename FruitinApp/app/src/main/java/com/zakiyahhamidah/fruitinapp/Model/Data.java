package com.zakiyahhamidah.fruitinapp.Model;

import java.io.Serializable;

public class Data implements Serializable {
    private int image;

    public Data(int image){
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
