package com.goacademy.gui.front;


import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.entities.Commande;
import com.goacademy.entities.Produit;
import com.goacademy.services.CommandeService;
import com.goacademy.utils.AlertUtils;

public class AjouterCommande extends Form {


    Commande currentCommande;

    TextField nombreProduitTF;
    Label nombreProduitLabel;
    Label prixLabel;
    Label prixTotalLabel;


    Label selectedProduitLabel;
    Produit selectedProduit = null;

    Button manageButton;

    Form previous;

    int quantite = 1;

    public AjouterCommande(Form previous) {
        super("Ajouter une commande", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        selectedProduit = AfficherToutProduit.currentProduit;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        selectedProduitLabel = new Label("Produit selectionné : " + selectedProduit.getReference());

        nombreProduitLabel = new Label("Quantite : ");
        nombreProduitLabel.setUIID("labelDefault");
        nombreProduitTF = new TextField();
        nombreProduitTF.setHint("Tapez la quantite");
        nombreProduitTF.addDataChangedListener((i, i1) -> {
            if (!nombreProduitTF.getText().equals("")){
                int selectedQtt = 0;
                try {
                    selectedQtt = (int) Float.parseFloat(nombreProduitTF.getText());
                } catch (NumberFormatException e) {
                    Dialog.show("Avertissement", nombreProduitTF.getText() + " n'est pas un nombre valide", new Command("Ok"));
                    nombreProduitTF.setText("");
                }

                quantite = selectedQtt;
                prixTotalLabel.setText("PrixTotal : " + selectedProduit.getPrix() * quantite);
                this.refreshTheme();
                this.repaint();
                this.revalidate();
            }
        });


        prixLabel = new Label("Prix du produit : " + selectedProduit.getPrix());
        prixLabel.setUIID("labelDefault");


        prixTotalLabel = new Label("PrixTotal : " + selectedProduit.getPrix() * quantite);
        prixTotalLabel.setUIID("labelDefault");


        manageButton = new Button("Commander");
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                selectedProduitLabel,
                nombreProduitLabel, nombreProduitTF,
                prixLabel,
                prixTotalLabel,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentCommande == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommandeService.getInstance().add(
                            new Commande(
                                    selectedProduit,
                                    (int) Float.parseFloat(nombreProduitTF.getText()),
                                    (int) (selectedProduit.getPrix() * quantite)
                            )
                    );
                    if (responseCode == 200) {
                        AlertUtils.makeNotification("Commande ajouté avec succes");
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de commande. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((AfficherToutProduit) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (nombreProduitTF.getText().equals("")) {
            Dialog.show("Avertissement", "NombreProduit vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(nombreProduitTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", nombreProduitTF.getText() + " n'est pas un nombre valide (nombreProduit)", new Command("Ok"));
            return false;
        }

        if (selectedProduit == null) {
            Dialog.show("Avertissement", "Veuillez choisir un produit", new Command("Ok"));
            return false;
        }


        return true;
    }
}