package com.goacademy.gui.back;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.entities.*;
import com.goacademy.services.*;
import com.goacademy.utils.*;

public class GererCategorie extends Form {

    

    Categorie currentCategorie;

    TextField nomTF;
    Label nomLabel;
    
    
    
    
    Button manageButton;

    Form previous;

    public GererCategorie(Form previous) {
        super(AfficherToutCategorie.currentCategorie == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCategorie = AfficherToutCategorie.currentCategorie;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        

        
        
        nomLabel = new Label("Nom : ");
        nomLabel.setUIID("labelDefault");
        nomTF = new TextField();
        nomTF.setHint("Tapez le nom");
        
        
        

        if (currentCategorie == null) {
            
            
            manageButton = new Button("Ajouter");
        } else {
            nomTF.setText(currentCategorie.getNom());
            
            
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            
            nomLabel, nomTF,
            
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        if (currentCategorie == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CategorieService.getInstance().add(
                            new Categorie(
                                    
                                    
                                    nomTF.getText()
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Categorie ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de categorie. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CategorieService.getInstance().edit(
                            new Categorie(
                                    currentCategorie.getId(),
                                    
                                    
                                    nomTF.getText()

                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Categorie modifié avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de categorie. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((AfficherToutCategorie) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        if (nomTF.getText().equals("")) {
            Dialog.show("Avertissement", "Nom vide", new Command("Ok"));
            return false;
        }
        
        
        

        

        
             
        return true;
    }
}