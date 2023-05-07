package com.goacademy.gui.back;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.entities.Commande;
import com.goacademy.services.CommandeService;
import com.goacademy.utils.Statics;

import java.util.ArrayList;
import java.util.Collections;

public class AfficherToutCommande extends Form {

    Form previous;

    public static Commande currentCommande = null;


    PickerComponent sortPicker;
    ArrayList<Component> componentModels;

    public AfficherToutCommande(Form previous) {
        super("Commandes", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Commande> listCommandes = CommandeService.getInstance().getAll();

        componentModels = new ArrayList<>();

        sortPicker = PickerComponent.createStrings("Produit", "NombreProduit", "PrixTotal").label("Trier par");
        sortPicker.getPicker().setSelectedString("");
        sortPicker.getPicker().addActionListener((l) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            Statics.compareVar = sortPicker.getPicker().getSelectedString();
            Collections.sort(listCommandes);
            for (Commande commande : listCommandes) {
                Component model = makeCommandeModel(commande);
                this.add(model);
                componentModels.add(model);
            }
            this.revalidate();
        });
        this.add(sortPicker);

        if (listCommandes.size() > 0) {
            for (Commande commande : listCommandes) {
                Component model = makeCommandeModel(commande);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    Label produitLabel, nombreProduitLabel, prixTotalLabel;


    private Container makeModelWithoutButtons(Commande commande) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");


        produitLabel = new Label("Produit : " + commande.getProduit());
        produitLabel.setUIID("labelDefault");

        nombreProduitLabel = new Label("NombreProduit : " + commande.getNombreProduit());
        nombreProduitLabel.setUIID("labelDefault");

        prixTotalLabel = new Label("PrixTotal : " + commande.getPrixTotal());
        prixTotalLabel.setUIID("labelDefault");

        produitLabel = new Label("Produit : " + commande.getProduit().getReference());
        produitLabel.setUIID("labelDefault");


        commandeModel.addAll(

                produitLabel, nombreProduitLabel, prixTotalLabel
        );

        return commandeModel;
    }

    Button deleteBtn;
    Container btnsContainer;

    private Component makeCommandeModel(Commande commande) {

        Container commandeModel = makeModelWithoutButtons(commande);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commande ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommandeService.getInstance().delete(commande.getId());

                if (responseCode == 200) {
                    currentCommande = null;
                    dlg.dispose();
                    commandeModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, deleteBtn);


        commandeModel.add(btnsContainer);

        return commandeModel;
    }

}