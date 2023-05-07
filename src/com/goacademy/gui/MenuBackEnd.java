package com.goacademy.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.goacademy.gui.back.AccueilBack;
import com.goacademy.gui.back.GestionPerformance;
import com.goacademy.gui.back.ShowAll;
import com.goacademy.gui.front.AccueilFront;

public class MenuBackEnd extends Form {
    public static Form loginForm;

    public MenuBackEnd() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {
        Button CoursBtn = new Button("Nos Cours");
        CoursBtn.setUIID("buttonMenu");
        CoursBtn.addActionListener(l -> new AccueilFront().show());
        this.add(CoursBtn);

        Button ProduitBtn = new Button("Nos Produit");
        ProduitBtn.setUIID("buttonMenu");
        ProduitBtn.addActionListener(l -> new AccueilBack().show());
        this.add(ProduitBtn);
        Button EquipeBtn = new Button("Nos Equipes");
        EquipeBtn.setUIID("buttonMenu");
        EquipeBtn.addActionListener(l -> new AccueilBack().show());
        this.add(EquipeBtn);
        
        
       Button PerfBtn = new Button("les Performances de Nos Joueurs");
        PerfBtn.setUIID("buttonMenu");
        PerfBtn.addActionListener(l -> new ShowAll().show());
        this.add(PerfBtn);
    }
}
