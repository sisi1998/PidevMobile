/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.gui.back;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

import com.goacademy.entities.Cours;
import com.goacademy.services.ServiceCours;


/**
 *
 * @author Lenovo
 */
public class ModifierCoursForm extends Form {
    
    Form current;
    public ModifierCoursForm(Cours r,Form previous) {
        setTitle("Modifier Cours");
        
        TextField nom = new TextField(r.getNom() , "nom" , 20 , TextField.ANY);
        TextField description = new TextField(r.getDescription() , "Description" , 20 , TextField.ANY);
      
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setNom(nom.getText());
           r.setDescription(description.getText());
               
           
       //appel fonction modfier avis men service
       
       if(ServiceCours.getInstance().modifierCours(r)) { // if true
           new ListCoursForm(previous).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListCoursForm(previous).show();
       });
       
        Container content = BoxLayout.encloseY(
                new FloatingHint(nom),
                new FloatingHint(description),
               
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        
        
    }
}
