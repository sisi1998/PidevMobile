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
import com.goacademy.entities.Cours;
import com.goacademy.entities.Equipe;


import com.goacademy.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Lenovo
 */
public class ServiceCours{
    
    //singleton 
    public static ServiceCours instance = null ;
    
    public static boolean resultOk = true;

    //initilisation connection request 
    private ConnectionRequest req;
    ArrayList<Arena> Arena;
      ArrayList<Equipe> Equipe;
               
    
    public static ServiceCours getInstance() {
        if(instance == null )
            instance = new ServiceCours();
        return instance ;
    }
    
    
    
    public ServiceCours() {
        req = new ConnectionRequest();
        
    }
    
    
    //ajout 
    public void ajoutCours(Cours t) {
        
        String url =Statics.BASE_URL+"/AddCoursJSON?Nom="+t.getNom()+"&Description="+t.getDescription();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
    
    
    //affichage
    
    public ArrayList<Cours>affichageCours() {
        ArrayList<Cours> result = new ArrayList<>();
          
        String url = Statics.BASE_URL+"/AllCoursJSON";
 req.setUrl(url);
 
 req.addResponseListener(new ActionListener<NetworkEvent>() {
     @Override
     public void actionPerformed(NetworkEvent evt) {
         JSONParser jsonp ;
         jsonp = new JSONParser();
         
         try {
             Map<String,Object>mapCours = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
             
             List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapCours.get("root");
             
             for(Map<String, Object> obj : listOfMaps) {
                 Cours re = new Cours();
                 
                 Map<String,Object> v = (Map<String,Object>)obj.get("arenas");
               Arena voy = new Arena((int) Float.parseFloat(v.get("id").toString()));
                
               Map<String,Object> ee = (Map<String,Object>)obj.get("equipex");
           Equipe e = new Equipe((int) Float.parseFloat(ee.get("id").toString()));
                
                 System.out.println(voy);
                

                       
                      
                                       float id = Float.parseFloat(obj.get("id").toString());
                    String nom = obj.get("Nom").toString();
                    String description = obj.get("description").toString();
                    
                   
                    re.setId((int)id);
                    re.setNom(nom);
                      re.setDescription(description);
                  re.setArenas(voy);
                re.setEquipex(e);
                
                     //Date
                String DateConverter =  obj.get("dateseance").toString().substring(obj.get("dateseance").toString().indexOf("2") ,obj.get("dateseance").toString().lastIndexOf("T"));
                
 
                re.setDateseance(DateConverter);
                 //Date
              //  String DateConverter =  obj.get("Date").toString().substring(obj.get("Date").toString().indexOf("2") ,obj.get("Date").toString().lastIndexOf("T"));
                
 
              //  re.setDateCreation(DateConverter);
                
                    
  //re.setDateC(DateConverter);
                      //re.setDateCreation(DateConverter);
                    
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
    public boolean deleteCours (int id ) {
        String url = Statics.BASE_URL +"/DeleteCoursJSON"+id;
        
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
    public boolean modifierCours (Cours  t) {
        String url = Statics.BASE_URL +"/UpdateCoursJSON?id="+t.getId()+"&Nom="+t.getNom()+"&Description="+t.getDescription();
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