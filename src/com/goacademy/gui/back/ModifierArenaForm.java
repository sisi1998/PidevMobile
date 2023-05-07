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
import com.goacademy.entities.Arena;
import com.goacademy.services.ServiceArena;


/**
 *
 * @author Lenovo
 */
public class ModifierArenaForm extends Form {
    
    Form current;
    public ModifierArenaForm(Arena r,Form previous) {
        setTitle("Modifier Arena");
        
        TextField nom = new TextField(r.getNom() , "nom" , 20 , TextField.ANY);
        TextField adresse = new TextField(r.getAdresse() , "adresse" , 20 , TextField.ANY);
    TextField surface = new TextField(String.valueOf(r.getSurface()) , "surface" , 20 , TextField.ANY);           
        
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
       
       //Event onclick btnModifer
       
       btnModifier.addPointerPressedListener(l ->   { 
           
           r.setNom(nom.getText());
           r.setAdresse(adresse.getText());
        r.setSurface(surface.getText());
                
           
       //appel fonction modfier avis men service
       
       if(ServiceArena.getInstance().modifierArena(r)) { // if true
           new ListArenaForm(previous).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListArenaForm(previous).show();
       });
       
        Container content = BoxLayout.encloseY(
                new FloatingHint(nom),
                new FloatingHint(adresse),
                new FloatingHint(surface),
                btnModifier,
                btnAnnuler
                
               
        );
        
        add(content);
        
        
    }
}
