package com.goacademy.gui;

import com.goacademy.MainApp;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;

public class Login extends Form {

    public static Form loginForm;

    public Login() {
        super("Connexion", new BoxLayout(BoxLayout.Y_AXIS));
        loginForm = this;
        addGUIs();
    }

    private void addGUIs() {

        
        Button frontendBtn = new Button("Espace utilisateur");
        frontendBtn.setUIID("buttonMenu");
        frontendBtn.addActionListener(l -> new com.goacademy.gui.front.AccueilFront().show());
        this.add(frontendBtn);
        
        
        Button backendBtn = new Button("Espace administrateur");
        backendBtn.setUIID("buttonMenu");
        backendBtn.addActionListener(l -> new MenuBackEnd().show());

        this.add(backendBtn);
        
        
    }
    
    
}
