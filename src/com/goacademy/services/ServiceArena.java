/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.goacademy.entities.Arena;

import com.goacademy.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class ServiceArena{
    
    //singleton 
    public static ServiceArena instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServiceArena getInstance() {
        if(instance == null )
            instance = new ServiceArena();
        return instance ;
    }
    
    
    
    public ServiceArena() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutArena(Arena t) {
        
        String url =Statics.BASE_URL+"/AddArenaJSON?Nom="+t.getNom()+"&Adresse="+t.getAdresse()+"&Surface="+t.getSurface();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    
    //affichage
    
    public ArrayList<Arena>affichageArena() {
        ArrayList<Arena> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/AllArenaJSON";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapArena = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapArena.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Arena re = new Arena();
                        float id = Float.parseFloat(obj.get("id").toString());
                        String nom = obj.get("nom").toString();
                        String adresse = obj.get("adresse").toString();
                        String surface = obj.get("surface").toString();
                       
                        re.setId((int)id);
                        re.setNom(nom);
                          re.setAdresse(adresse);
                      re.setSurface(surface);
                     
                   
                     //Date
              //  String DateConverter =  obj.get("updateAt").toString().substring(obj.get("updateAt").toString().indexOf("2") ,obj.get("updateAt").toString().lastIndexOf("T"));
                
 
                //re.setUpdateAt(DateConverter);
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        System.out.println(result);
        return result;
        
        
    }
    
    
    
  
    
    //Delete 
    public boolean deleteArena (int id ) {
        String url = Statics.BASE_URL +"/DeleteArenaJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    
    
    //Update 
    public boolean modifierArena (Arena  t) {
        String url = Statics.BASE_URL +"/UpdateArenaJSON?id="+t.getId()+"&Nom="+t.getNom()+"&Adresse="+t.getAdresse()+"&Surface="+t.getSurface();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    

    
}