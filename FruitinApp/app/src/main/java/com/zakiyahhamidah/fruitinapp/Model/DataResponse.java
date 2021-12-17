package com.zakiyahhamidah.fruitinapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataResponse implements Serializable {

    private int id;
    private String name;
    private String family;
    private String genus;
    private String order;
    @SerializedName("nutritions")
    private Nutritions nutritions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }
    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Nutritions getNutritions() {
        return nutritions;
    }

    public void setNutritions(Nutritions nutritions) {
        this.nutritions = nutritions;
    }

}
