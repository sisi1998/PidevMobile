package com.goacademy.gui.front;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.goacademy.entities.Produit;
import com.goacademy.services.ProduitService;
import com.goacademy.utils.Statics;

import java.util.ArrayList;

public class AfficherToutProduit extends Form {

    Form previous;

    Resources theme = UIManager.initFirstTheme("/theme");

    public static Produit currentProduit = null;

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

    }

    Label categorieLabel, genreLabel, descriptionLabel, referenceLabel, imageLabel, prixLabel, stockLabel;

    ImageViewer imageIV;

    Button commanderBtn;

    private Container makeModelWithoutButtons(Produit produit) {
        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");

        commanderBtn = new Button("Commander");
        commanderBtn.setUIID("buttonWhiteCenter");
        commanderBtn.addActionListener(action -> {
            currentProduit = produit;
            new AjouterCommande(this).show();
        });

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
                categorieLabel, genreLabel, descriptionLabel, referenceLabel, prixLabel, stockLabel,
                commanderBtn
        );

        return produitModel;
    }


    Container btnsContainer;

    private Component makeProduitModel(Produit produit) {

        Container produitModel = makeModelWithoutButtons(produit);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");


        produitModel.add(btnsContainer);

        return produitModel;
    }

}