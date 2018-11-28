package com.felixawpw.navigation.GoogleAPI;

import com.google.android.gms.location.places.PlaceLikelihood;

public class Place {
    //<editor-fold defaultstate="collapsed" desc="ENCAPSULATED FIELD">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(float likelihood) {
        this.likelihood = likelihood;
    }
    //</editor-fold>

    private String id;
    private String name;
    private float likelihood;

    public Place() {

    }

    public Place(String id, String name, float likelihood) {
        this.setId(id);
        this.setName(name);
        this.setLikelihood(likelihood);
    }

    public Place(PlaceLikelihood placeLikelihood) {
        this.setId(placeLikelihood.getPlace().getId());
        this.setName(placeLikelihood.getPlace().getName().toString());
        this.setLikelihood(placeLikelihood.getLikelihood());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
