package com.goacademy.gui.back;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.goacademy.gui.Login;
import java.util.Hashtable;

public class AccueilBack extends Form {

    public AccueilBack() {
        super("Menu", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        this.getToolbar().setUIID("TitleArea");
    }

    private void addGUIs() {
        Label label = new Label("Espace administrateur");
        label.setUIID("links");
        Button btnDeconnexion = new Button();
        btnDeconnexion.setMaterialIcon(FontImage.MATERIAL_ARROW_FORWARD);
        btnDeconnexion.addActionListener(action -> Login.loginForm.showBack());

        Container userContainer = new Container(new BorderLayout());
        userContainer.setUIID("userContainer");
        userContainer.add(BorderLayout.CENTER, label);
        userContainer.add(BorderLayout.EAST, btnDeconnexion);

        Container menuContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        menuContainer.addAll(
                userContainer,
                makeCategoriesButton(),
                makeCommandesButton(),
                makeProduitsButton()
        );

        this.add(menuContainer);
    }

    private Button makeCategoriesButton() {
        Button button = new Button("Categories");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_CATEGORY);
        button.addActionListener(action -> new AfficherToutCategorie(this).show());
        return button;
    }

    private Button makeCommandesButton() {
        Button button = new Button("Commandes");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_ADD_SHOPPING_CART);
        button.addActionListener(action -> new AfficherToutCommande(this).show());
        return button;
    }

    private Button makeProduitsButton() {
        Button button = new Button("Produits");
        button.setUIID("buttonMenu");
        button.setMaterialIcon(FontImage.MATERIAL_ARCHIVE);
        button.addActionListener(action -> new AfficherToutProduit(this).show());
        return button;
    }

  static {
    // Set the theme to blue
    Resources themeProps = UIManager.initNamedTheme("/theme", "Theme_Blue");
  
}


}
