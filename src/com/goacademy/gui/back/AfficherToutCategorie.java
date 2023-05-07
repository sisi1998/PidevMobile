package com.goacademy.gui.back;

import com.codename1.components.*;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.goacademy.entities.Categorie;
import com.goacademy.services.CategorieService;

import java.util.*;

public class AfficherToutCategorie extends Form {

    Form previous; 
    
    public static Categorie currentCategorie = null;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    

    public AfficherToutCategorie(Form previous) {
        super("Categories", new BoxLayout(BoxLayout.Y_AXIS));
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
        

        ArrayList<Categorie> listCategories = CategorieService.getInstance().getAll();
        componentModels = new ArrayList<>();
        
        searchTF = new TextField("", "Chercher categorie par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Categorie categorie : listCategories) {
                if (categorie.getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCategorieModel(categorie);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);
        
        
        if (listCategories.size() > 0) {
            for (Categorie categorie : listCategories) {
                Component model = makeCategorieModel(categorie);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }
    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCategorie = null;
            new GererCategorie(this).show();
        });
        
    }
    Label nomLabel  ;
    

    private Container makeModelWithoutButtons(Categorie categorie) {
        Container categorieModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        categorieModel.setUIID("containerRounded");
        
        
        nomLabel = new Label("Nom : " + categorie.getNom());
        nomLabel.setUIID("labelDefault");
        

        categorieModel.addAll(
                
                nomLabel
        );

        return categorieModel;
    }
    
    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makeCategorieModel(Categorie categorie) {

        Container categorieModel = makeModelWithoutButtons(categorie);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");
        
        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCategorie = categorie;
            new GererCategorie(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce categorie ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CategorieService.getInstance().delete(categorie.getId());

                if (responseCode == 200) {
                    currentCategorie = null;
                    dlg.dispose();
                    categorieModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du categorie. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);
        
        
        categorieModel.add(btnsContainer);

        return categorieModel;
    }
    
}