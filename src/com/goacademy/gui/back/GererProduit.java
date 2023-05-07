package com.goacademy.gui.back;


import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.goacademy.entities.*;
import com.goacademy.services.*;
import com.goacademy.utils.*;

import java.io.IOException;
import java.util.*;

public class GererProduit extends Form {

    
    Resources theme = UIManager.initFirstTheme("/theme");
    String selectedImage;
    boolean imageEdited = false;
    

    Produit currentProduit;

    TextField genreTF;TextField descriptionTF;TextField referenceTF;TextField imageTF;TextField prixTF;TextField stockTF;
    Label genreLabel;Label descriptionLabel;Label referenceLabel;Label imageLabel;Label prixLabel;Label stockLabel;
    
    
    ArrayList<Categorie> listCategories;
    PickerComponent categoriePC;
    Categorie selectedCategorie = null;
    
    
    ImageViewer imageIV;
    Button selectImageButton;
    
    Button manageButton;

    Form previous;

    public GererProduit(Form previous) {
        super(AfficherToutProduit.currentProduit == null ?  "Ajouter" :  "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentProduit = AfficherToutProduit.currentProduit;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {
        
        String[] categorieStrings;
        int categorieIndex;
        categoriePC = PickerComponent.createStrings("").label("Categorie");
        listCategories = CategorieService.getInstance().getAll();
        categorieStrings = new String[listCategories.size()];
        categorieIndex = 0;
        for (Categorie categorie : listCategories) {
            categorieStrings[categorieIndex] = categorie.getNom();
            categorieIndex++;
        }
        if(listCategories.size() > 0) {
            categoriePC.getPicker().setStrings(categorieStrings);
            categoriePC.getPicker().addActionListener(l -> selectedCategorie = listCategories.get(categoriePC.getPicker().getSelectedStringIndex()));
        } else {
            categoriePC.getPicker().setStrings("");
        }
        

        
        
        
        
        genreLabel = new Label("Genre : ");
        genreLabel.setUIID("labelDefault");
        genreTF = new TextField();
        genreTF.setHint("Tapez le genre");
        
        
        
        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");
        
        
        
        referenceLabel = new Label("Reference : ");
        referenceLabel.setUIID("labelDefault");
        referenceTF = new TextField();
        referenceTF.setHint("Tapez le reference");
        
        
        
        
        
        
        
        prixLabel = new Label("Prix : ");
        prixLabel.setUIID("labelDefault");
        prixTF = new TextField();
        prixTF.setHint("Tapez le prix");
        
        
        
        stockLabel = new Label("Stock : ");
        stockLabel.setUIID("labelDefault");
        stockTF = new TextField();
        stockTF.setHint("Tapez le stock");
        
        
        
        imageLabel = new Label("Image : ");
        imageLabel.setUIID("labelDefault");
        selectImageButton = new Button("Ajouter une image");

        if (currentProduit == null) {
            
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            
            
            manageButton = new Button("Ajouter");
        } else {
            
            genreTF.setText(currentProduit.getGenre());
            descriptionTF.setText(currentProduit.getDescription());
            referenceTF.setText(currentProduit.getReference());
            
            prixTF.setText(String.valueOf(currentProduit.getPrix()));
            stockTF.setText(String.valueOf(currentProduit.getStock()));
            
            categoriePC.getPicker().setSelectedString(currentProduit.getCategorie().getNom());
            selectedCategorie = currentProduit.getCategorie();
            
            
            if (currentProduit.getImage() != null) {
                selectedImage = currentProduit.getImage();
                String url = Statics.PRODUIT_IMAGE_URL + currentProduit.getImage();
                Image image = URLImage.createToStorage(
                        EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                        url,
                        url,
                        URLImage.RESIZE_SCALE
                );
                imageIV = new ImageViewer(image);
            } else {
                imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
            }
            imageIV.setFocusable(false);
            

            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
            imageLabel, imageIV,
            selectImageButton,
            
            genreLabel, genreTF,
            descriptionLabel, descriptionTF,
            referenceLabel, referenceTF,
            
            prixLabel, prixTF,
            stockLabel, stockTF,
            categoriePC,
            manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        
        selectImageButton.addActionListener(a -> {
            selectedImage = Capture.capturePhoto(900, -1);
            try {
                imageEdited = true;
                imageIV.setImage(Image.createImage(selectedImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectImageButton.setText("Modifier l'image");
        });
        
        if (currentProduit == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().add(
                            new Produit(
                                    
                                    
                                    selectedCategorie,
                                    genreTF.getText(),
                                    descriptionTF.getText(),
                                    referenceTF.getText(),
                                    selectedImage,
                                    Float.parseFloat(prixTF.getText()),
                                    (int) Float.parseFloat(stockTF.getText())
                            )
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Produit ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de produit. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ProduitService.getInstance().edit(
                            new Produit(
                                    currentProduit.getId(),
                                    
                                    
                                    selectedCategorie,
                                    genreTF.getText(),
                                    descriptionTF.getText(),
                                    referenceTF.getText(),
                                    selectedImage,
                                    Float.parseFloat(prixTF.getText()),
                                    (int) Float.parseFloat(stockTF.getText())

                            ), imageEdited
                    );
                    if (responseCode == 200) {
                         Dialog.show("Succés", "Produit modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de produit. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh(){
        ((AfficherToutProduit) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {

        
        
        
        
        
        if (genreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Genre vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        if (referenceTF.getText().equals("")) {
            Dialog.show("Avertissement", "Reference vide", new Command("Ok"));
            return false;
        }
        
        
        
        
        
        
        
        
        if (prixTF.getText().equals("")) {
            Dialog.show("Avertissement", "Prix vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(prixTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", prixTF.getText() + " n'est pas un nombre valide (prix)", new Command("Ok"));
            return false;
        }
        
        
        
        if (stockTF.getText().equals("")) {
            Dialog.show("Avertissement", "Stock vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(stockTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", stockTF.getText() + " n'est pas un nombre valide (stock)", new Command("Ok"));
            return false;
        }
        
        
        

        
        if (selectedCategorie == null) {
            Dialog.show("Avertissement", "Veuillez choisir un categorie", new Command("Ok"));
            return false;
        }
        

        
        if (selectedImage == null) {
            Dialog.show("Avertissement", "Veuillez choisir une image", new Command("Ok"));
            return false;
        }
        
             
        return true;
    }
}