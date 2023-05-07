/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.gui.back;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.entities.Arena;
import com.goacademy.services.ServiceArena;


/**
 *
 * @author bhk
 */
public class AjoutArenaForm extends Form{

    public AjoutArenaForm(Form previous) {
        setTitle("Add a new Arena");
        setLayout(BoxLayout.y());
        
        TextField nom = new TextField("","nom");
        TextField adresse= new TextField("", "adresse");
        TextField surface= new TextField("", "surface");
         TextField updateAt= new TextField("", "updateAt");
        Button btnValider = new Button("Add Arena");
        Button btnAfficher = new Button("Afficher Arena");
        btnAfficher.addActionListener((evt) -> {
            new ListArenaForm(previous).show();
        });
       btnValider.addActionListener((e) -> {
            
            
            try {
                
                if(surface.getText().equals("") || adresse.getText().equals("")|| nom.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    
                    //njibo iduser men session (current user)
                    Arena a = new Arena(   String.valueOf(nom.getText()),
                                  String.valueOf(adresse.getText()),
                                  String.valueOf(surface.getText()),
                    String.valueOf(updateAt.getText()));
                    
                    System.out.println("data  Commande == "+a);
                    
                    
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceArena.getInstance().ajoutArena(a);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListCommandeForm
                    new ListArenaForm(previous).show();
                    
                    
                    refreshTheme();//Actualisation
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        addAll(nom,adresse,updateAt,surface,btnValider,btnAfficher);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
