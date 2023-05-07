/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.entities;

/**
 *
 * @author Siwar
 */
public class Performance {
    
    
    private int id;
    private String Mins,Apps,Buts;
    private String Nom;
    private User joueur;

    public User getJoueur() {
        return joueur;
    }

    public void setJoueur(User joueur) {
        this.joueur = joueur;
    }
    
    
    
    
    public Performance() {
    }

    public Performance(int id, String Minus, String Apps, String Buts) {
        this.id = id;
        this.Mins = Minus;
        this.Apps = Apps;
        this.Buts = Buts;
    }

    public Performance(String Nom, String Minus, String Apps, String Buts) {
        this.Mins = Minus;
        this.Apps = Apps;
        this.Nom =Nom;        
        this.Buts = Buts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMins() {
        return Mins;
    }

    public void setMins(String Minus) {
        this.Mins = Minus;
    }

    public String getApps() {
        return Apps;
    }

    public void setApps(String Apps) {
        this.Apps = Apps;
    }

    public String getButs() {
        return Buts;
    }

    public void setButs(String Buts) {
        this.Buts = Buts;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }
    
    
    
    
    
}
