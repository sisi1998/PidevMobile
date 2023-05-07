package com.goacademy.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.goacademy.entities.*;
import com.goacademy.utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandeService {

    public static CommandeService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commande> listCommandes;

    

    private CommandeService() {
        cr = new ConnectionRequest();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }
    
    public ArrayList<Commande> getAll() {
        listCommandes = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommandes = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCommandes;
    }

    private ArrayList<Commande> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commande commande = new Commande(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        
                        makeProduit((Map<String, Object>) obj.get("produit")),
                        (int) Float.parseFloat(obj.get("nombreProduit").toString()),
                        (int) Float.parseFloat(obj.get("prixTotal").toString())
                        
                );

                listCommandes.add(commande);
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
        return listCommandes;
    }
    
    public Produit makeProduit(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Produit produit = new Produit();
        produit.setId((int) Float.parseFloat(obj.get("id").toString()));
        produit.setReference((String) obj.get("reference"));
        return produit;
    }
    
    public int add(Commande commande) {
        return manage(commande, false);
    }

    public int edit(Commande commande) {
        return manage(commande, true );
    }

    public int manage(Commande commande, boolean isEdit) {
        
        cr = new ConnectionRequest();

        
        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/commande/edit");
            cr.addArgument("id", String.valueOf(commande.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/commande/add");
        }
        
        cr.addArgument("produit", String.valueOf(commande.getProduit().getId()));
        cr.addArgument("nombreProduit", String.valueOf(commande.getNombreProduit()));
        cr.addArgument("prixTotal", String.valueOf(commande.getPrixTotal()));
        
        
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int commandeId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commande/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commandeId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
