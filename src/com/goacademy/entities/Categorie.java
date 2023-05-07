package com.goacademy.entities;

import java.util.Date;
import com.goacademy.utils.*;

public class Categorie {
    
    private int id;
     private String nom;
    
    public Categorie() {}

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
}