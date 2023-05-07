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
import com.goacademy.entities.Cours;
import com.goacademy.services.ServiceCours;


/**
 *
 * @author bhk
 */
public class AjoutCoursForm extends Form{

    public AjoutCoursForm(Form previous) {
        setTitle("Add a new Cours");
        setLayout(BoxLayout.y());
        
        TextField nom = new TextField("","nom");
        TextField description= new TextField("", "Description");
       // TextField dateseance= new TextField("", "Dateseance");
        
        Button btnValider = new Button("Add Cours");
        Button btnAfficher = new Button("Afficher Cours");
        btnAfficher.addActionListener((evt) -> {
            new ListCoursForm(previous).show();
        });
       btnValider.addActionListener((e) -> {
            
            
            try {
                
                if(description.getText().equals("") ||  nom.getText().equals("")) {
                    Dialog.show("Veuillez vérifier les données","","Annuler", "OK");
                }
                
                else {
                    InfiniteProgress ip = new InfiniteProgress();; //Loading  after insert data
                
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    
                    
                    //njibo iduser men session (current user)
                    Cours a = new Cours( String.valueOf(nom.getText()),
                                
                                  String.valueOf(description.getText()));
                      
                                  
                    System.out.println("data  Commande == "+a);
                    
                    
                    //appelle methode ajouterReclamation mt3 service Reclamation bch nzido données ta3na fi base 
                    ServiceCours.getInstance().ajoutCours(a);
                    
                    iDialog.dispose(); //na7io loading ba3d ma3mlna ajout
                    
                    //ba3d ajout net3adaw lel ListCommandeForm
                    new ListCoursForm(previous).show();
                    
                    
                    refreshTheme();//Actualisation
                            
                }
                
            }catch(Exception ex ) {
                ex.printStackTrace();
            }
            
            
            
            
            
        });
        
        addAll(nom,description,btnValider,btnAfficher);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
