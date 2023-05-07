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
import com.goacademy.entities.User;
import com.goacademy.utils.Statics;

import com.goacademy.entities.Performance;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class ServicePerformance {
    
    //singleton 
    public static ServicePerformance instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    
    
    public static ServicePerformance getInstance() {
        if(instance == null )
            instance = new ServicePerformance();
        return instance ;
    }
    
    
        

    public ServicePerformance() {
        req = new ConnectionRequest();
        
    }
    //ajout 
    public void ajoutPerformance(Performance perf, String mail) {
        
        String url =Statics.BASE_URL+"addPerf?Nom="+perf.getNom()+"&Apps="+perf.getApps()+"&Mins="+perf.getMins()+"&Buts="+perf.getButs();
                
                 req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
    
    public ArrayList<Performance>affichagePerformances() {
        ArrayList<Performance> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"listPerf";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapPerformances = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapPerformances.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Performance re = new Performance();
                        
                        
                        //dima id fi codename one float 5outhouha
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                      //  String objet = obj.get("Nom").toString();    
                        String Mins = obj.get("Mins").toString();
                        String apps = obj.get("Apps").toString();        
                        String buts = obj.get("Buts").toString();

                    //    float etat = Float.parseFloat(obj.get("etat").toString());
                        
                        re.setId((int)id);
                      //  re.setNom(objet);
                        re.setApps(apps);
                        re.setButs(buts);
                        re.setMins(Mins);
                        
                        
                        //insert data into ArrayList result
                        result.add(re);
                        
                        System.out.println("hiiii"+re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
        
        
    }

    
    
    
    //affichage
    
    public ArrayList<User>affichageJoueurs() {
        ArrayList<User> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"listJoueurs";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapPerformances = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapPerformances.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        User re = new User();
                        
                       
                        float id = Float.parseFloat(obj.get("id").toString());
                        
                        String objet = obj.get("Nom").toString();
                        re.setId((int)id);
                                
                    
                        
                      
                        re.setNom(objet);
                      
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);

        return result;
        
        
    }
    
    
    
  
    
    //Delete 
    public boolean deletePerformance(int id ) {
        String url = Statics.BASE_URL +"deletePer?id="+id;
        
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
    /*public boolean modifierPerformance(Performance perf) {
        String url = Statics.BASE_URL +"/updatePerformance?id="+perf.getId()+"&objet="+perf.getObjet()+"&description="+perf.getDescription()+"&etat="+perf.getEtat();
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
    

    */

}