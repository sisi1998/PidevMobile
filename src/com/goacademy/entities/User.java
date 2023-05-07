package com.goacademy.entities;

import java.util.Date;

public class User {
    
    private int id;
    String Nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {
    }

    public User(int id, String Nom) {
        this.id = id;
        this.Nom = Nom;
    }

    public User(String Nom) {
        this.Nom = Nom;
    }
    
  
    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

   
    
    
    
}


