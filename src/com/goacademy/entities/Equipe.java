/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.entities;

/**
 *
 * @author Mon Pc
 */
public class Equipe {
      private int id;
    private String Nom;

    public Equipe() {
    }

    public Equipe(int id, String Nom) {
        this.id = id;
        this.Nom = Nom;
    }

    public Equipe(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Equipe{" + "id=" + id + ", Nom=" + Nom + '}';
    }
    
}
