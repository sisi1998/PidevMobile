/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.entities;

import java.util.Date;

/**
 *
 * @author Salma
 */
public class Cours {
   
    private int id;
    Arena arenas;
    Equipe equipex;
    private String Nom,Description;
    private String dateseance;
    private Date dure;

    public Cours(int id, String Nom, String Description, String dateseance, Date dure) {
        this.id = id;
        this.Nom = Nom;
        this.Description = Description;
        this.dateseance = dateseance;
        this.dure = dure;
    }

    public Cours(int id, Arena arenas, Equipe equipex, String Nom, String Description, String dateseance, Date dure) {
        this.id = id;
        this.arenas = arenas;
        this.equipex = equipex;
        this.Nom = Nom;
        this.Description = Description;
        this.dateseance = dateseance;
        this.dure = dure;
    }

   

    public Cours() {
    }

    public Cours(String Nom, String Description) {
        this.Nom = Nom;
        this.Description = Description;
    }

    public Cours(String Nom, String Description, String dateseance, Date dure) {
        this.Nom = Nom;
        this.Description = Description;
        this.dateseance = dateseance;
        this.dure = dure;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDateseance() {
        return dateseance;
    }

    public void setDateseance(String dateseance) {
        this.dateseance = dateseance;
    }

    public Cours(int id, Arena arenas, Equipe equipex, String Nom, String Description, String dateseance) {
        this.id = id;
        this.arenas = arenas;
        this.equipex = equipex;
        this.Nom = Nom;
        this.Description = Description;
        this.dateseance = dateseance;
    }

    public Cours(String Nom, String Description, String dateseance) {
        this.Nom = Nom;
        this.Description = Description;
        this.dateseance = dateseance;
    }

   

    public Date getDure() {
        return dure;
    }

    public void setDure(Date dure) {
        this.dure = dure;
    }

    public Arena getArenas() {
        return arenas;
    }

    public void setArenas(Arena arenas) {
        this.arenas = arenas;
    }

    public Equipe getEquipex() {
        return equipex;
    }

    public void setEquipex(Equipe equipex) {
        this.equipex = equipex;
    }

  
    @Override
    public String toString() {
        return "Cours{" + "id=" + id + ", arenas=" + arenas + ", equipex=" + equipex + ", Nom=" + Nom + ", Description=" + Description + ", dateseance=" + dateseance + ", dure=" + dure + '}';
    }

    

}
