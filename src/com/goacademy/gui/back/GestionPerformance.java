/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy.gui.back;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.Table;
import com.codename1.ui.table.TableModel;
import com.goacademy.entities.Cours;
import com.goacademy.entities.Performance;
import com.goacademy.entities.User;
import com.goacademy.services.ServiceCours;
import com.goacademy.services.ServicePerformance;
import com.goacademy.utils.AlertUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Siwar
 */
public class GestionPerformance extends Form {
    
    
    
    Performance currentperformance;
 ComboBox<String> joueursLD;
    TextField appsTF;
   
    TextField minsTF;

    TextField butsTF;
    
    Label appsLabel;
    Label minsLabel, playerlabel;
    Label butsLabel;
    Label descriptionLabel;
    TextField emailTF;
  Label emailLabel;

    Button manageButton;
      Vector<String> VectorNiveau = new Vector();
            List<User> listJoueurs =   ServicePerformance.getInstance().affichageJoueurs();

    Form previous;
    
    public GestionPerformance (Form previous) {
       
      super(ShowAll.currentperformance == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

       currentperformance = ShowAll.currentperformance;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public GestionPerformance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addGUIs() {


       // dateTF = PickerComponent.createDate(null).label("Date");

   
           VectorNiveau.add("Choisir un joueur");
    
        

     for(User j : listJoueurs) {
         VectorNiveau.add(j.getNom());
         
     }
        
         joueursLD = new ComboBox<>(VectorNiveau);
        
        

        appsLabel = new Label("Apps : ");
        appsLabel.setUIID("labelDefault");
        appsTF = new TextField();
        appsTF.setHint("Tapez les apps");


        minsLabel = new Label("Mins : ");
        minsLabel.setUIID("labelDefault");
        minsTF = new TextField();
       minsTF.setHint("Tapez les mins");


        butsLabel = new Label("Buts : ");
        butsLabel.setUIID("labelDefault");
        butsTF = new TextField();
        butsTF.setHint("Tapez les Buts");
        
        
          emailLabel = new Label("Email : ");
        emailLabel.setUIID("labelDefault");
        emailTF = new TextField();
        emailTF.setHint("Tapez le description");

        if (currentperformance == null) {


            manageButton = new Button("Ajouter");
        } else {
         
            minsTF.setText(currentperformance.getMins());
            appsTF.setText(currentperformance.getApps());
            butsTF.setText(currentperformance.getButs());


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                 emailLabel, emailTF,
                appsLabel, minsTF,
                appsLabel, appsTF,
                descriptionLabel, 
                butsLabel, butsTF,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {
        //(String Minus, String Apps, String Buts,User joueur)

        if (currentperformance== null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                       String selectedJoueur = (String)joueursLD.getSelectedItem();
                    ServicePerformance.getInstance().ajoutPerformance(
                            new  Performance(
                                    selectedJoueur,
                                    appsTF.getText(),
                                    appsTF.getText(),
                                    butsTF.getText()
                                   
                            ),
                            emailTF.getText()
                    );
                   
                }
            });
        } 
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (appsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }


        if (butsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Type vide", new Command("Ok"));
            return false;
        }


        if (minsTF.getText().equals("")) {
            Dialog.show("Avertissement", "Etat vide", new Command("Ok"));
            return false;
        }


        if (emailTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        return true;
    }
    
}