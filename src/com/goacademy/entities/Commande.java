package com.goacademy.entities;

import java.util.Date;
import com.goacademy.utils.*;

public class Commande implements Comparable<Commande> {
    
    private int id;
    private Produit produit;
     private int nombreProduit;
     private int prixTotal;
    
    public Commande() {}

    public Commande(int id, Produit produit, int nombreProduit, int prixTotal) {
        this.id = id;
        this.produit = produit;
        this.nombreProduit = nombreProduit;
        this.prixTotal = prixTotal;
    }

    public Commande(Produit produit, int nombreProduit, int prixTotal) {
        this.produit = produit;
        this.nombreProduit = nombreProduit;
        this.prixTotal = prixTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public int getNombreProduit() {
        return nombreProduit;
    }

    public void setNombreProduit(int nombreProduit) {
        this.nombreProduit = nombreProduit;
    }
    
    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }
    
    
    @Override
    public int compareTo(Commande commande) {
        switch (Statics.compareVar) {
            case "Produit":
                return this.getProduit().getReference().compareTo(commande.getProduit().getReference());
            case "NombreProduit":
                return Integer.compare(this.getNombreProduit(), commande.getNombreProduit());
            case "PrixTotal":
                return Integer.compare(this.getPrixTotal(), commande.getPrixTotal());
            
            default:
                return 0;
        }
    }
    
}