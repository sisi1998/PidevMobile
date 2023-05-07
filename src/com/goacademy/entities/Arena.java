/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.entities;

import java.util.Date;

/**
 *
 * @author 
 */
public class Arena {
    private int id;
    private String Nom,Adresse,Image,Surface;
    private String UpdateAt;

    public Arena(int id, String Nom, String Adresse, String Image, String Surface, String UpdateAt) {
        this.id = id;
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Image = Image;
        this.Surface = Surface;
        this.UpdateAt = UpdateAt;
    }
    public Arena(int id, String Nom, String Adresse, String Image, String Surface) {
        this.id = id;
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Image = Image;
        this.Surface = Surface;
        
    }

    public Arena(int id) {
        this.id = id;
    }

    public Arena(String Nom, String Adresse, String Surface) {
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Surface = Surface;
    }

    public Arena(String Nom, String Adresse, String Image, String Surface, String UpdateAt) {
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Image = Image;
        this.Surface = Surface;
        this.UpdateAt = UpdateAt;
    }

    public Arena(String Nom, String Adresse, String Image, String Surface) {
        this.Nom = Nom;
        this.Adresse = Adresse;
        this.Image = Image;
        this.Surface = Surface;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getSurface() {
        return Surface;
    }

    public void setSurface(String Surface) {
        this.Surface = Surface;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

   

    

   

    

    

    public Arena() {
        
    }

    @Override
    public String toString() {
        return "Arena{" + "id=" + id + ", Nom=" + Nom + ", Adresse=" + Adresse + ", Image=" + Image + ", Surface=" + Surface + ", UpdateAt=" + UpdateAt + '}';
    }



    
    
}
