package com.goacademy.gui.back;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.*;
import com.goacademy.entities.Produit;
import com.goacademy.services.ProduitService;
import com.goacademy.utils.Statics;

import java.util.*;

public class AfficherToutProduit extends Form {

    Form previous; 
    
    Resources theme = UIManager.initFirstTheme("/theme");
    
    public static Produit currentProduit = null;
    Button addBtn;

    
    

    public AfficherToutProduit(Form previous) {
        super("Produits", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);
        

        ArrayList<Produit> listProduits = ProduitService.getInstance().getAll();
        
        
        if (listProduits.size() > 0) {
            for (Produit produit : listProduits) {
                Component model = makeProduitModel(produit);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentProduit = null;
            new GererProduit(this).show();
        });
        
    }
    Label categorieLabel   , genreLabel   , descriptionLabel   , referenceLabel   , imageLabel   , prixLabel   , stockLabel  ;
    
    ImageViewer imageIV;
    

    private Container makeModelWithoutButtons(Produit produit) {
        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");
        
        
        categorieLabel = new Label("Categorie : " + produit.getCategorie());
        categorieLabel.setUIID("labelDefault");
        
        genreLabel = new Label("Genre : " + produit.getGenre());
        genreLabel.setUIID("labelDefault");
        
        descriptionLabel = new Label("Description : " + produit.getDescription());
        descriptionLabel.setUIID("labelDefault");
        
        referenceLabel = new Label("Reference : " + produit.getReference());
        referenceLabel.setUIID("labelDefault");
        
        imageLabel = new Label("Image : " + produit.getImage());
        imageLabel.setUIID("labelDefault");
        
        prixLabel = new Label("Prix : " + produit.getPrix());
        prixLabel.setUIID("labelDefault");
        
        stockLabel = new Label("Stock : " + produit.getStock());
        stockLabel.setUIID("labelDefault");
        
        categorieLabel = new Label("Categorie : " + produit.getCategorie().getNom());
        categorieLabel.setUIID("labelDefault");
        
        if (produit.getImage() != null) {
            String url = Statics.PRODUIT_IMAGE_URL + produit.getImage();
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

        produitModel.addAll(
                imageIV,
                categorieLabel, genreLabel, descriptionLabel, referenceLabel, prixLabel, stockLabel
        );

        return produitModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeProduitModel(Produit produit) {

        Container produitModel = makeModelWithoutButtons(produit);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentProduit = produit;
            new GererProduit(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce produit ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ProduitService.getInstance().delete(produit.getId());

                if (responseCode == 200) {
                    currentProduit = null;
                    dlg.dispose();
                    produitModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du produit. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        produitModel.add(btnsContainer);

        return produitModel;
    }
    
}