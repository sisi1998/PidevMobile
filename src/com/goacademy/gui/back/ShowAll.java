package com.goacademy.gui.back;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.entities.Performance;
import com.goacademy.services.ServicePerformance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    static Performance currentperformance;

    Form previous;

    public static Performance currentPerformance = null;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;


    public ShowAll(Form previous) {
        super("Performances", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public ShowAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


        ArrayList<Performance> listPerformances = ServicePerformance.getInstance().affichagePerformances();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher performance par Joeur");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Performance performance : listPerformances) {
                if (performance.getJoueur().getNom().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makePerformanceModel(performance);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listPerformances.size() > 0) {
            for (Performance performance : listPerformances) {
                Component model = makePerformanceModel(performance);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentPerformance = null;
            new GestionPerformance(this).show();
        });

    }

    Label  playerLabel, minsLabel, appsLabel, butsLabel;


    private Container makeModelWithoutButtons(Performance performance) {
        Container performanceModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        performanceModel.setUIID("containerRounded");


        playerLabel = new Label("Joueur: " + performance.getJoueur());
        playerLabel.setUIID("labelDefault");

        minsLabel = new Label("Mins : " + performance.getMins());
        minsLabel.setUIID("labelDefault");

        appsLabel = new Label("Apps : " + performance.getApps());
        appsLabel.setUIID("labelDefault");

        butsLabel = new Label("Buts : " + performance.getButs());
        butsLabel.setUIID("labelDefault");


        performanceModel.addAll(

                playerLabel, minsLabel, appsLabel, butsLabel
        );

        return performanceModel;
    }

    Button editBtn, deleteBtn;
    Container btnsContainer;

    private Component makePerformanceModel(Performance performance) {

        Container performanceModel = makeModelWithoutButtons(performance);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentPerformance = performance;
            new GestionPerformance(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reclamation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                boolean responseCode =ServicePerformance.getInstance().deletePerformance(performance.getId());

                if (!responseCode) {
                    currentPerformance = null;
                    dlg.dispose();
                    performanceModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


       performanceModel.add(btnsContainer);

        return performanceModel;
    }

}
