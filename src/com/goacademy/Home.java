/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goacademy;

import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.goacademy.gui.back.AjoutArenaForm;
import com.goacademy.gui.back.AjoutCoursForm;
import com.goacademy.gui.back.ListArenaForm;
import com.goacademy.gui.back.ListCoursForm;
/**
 *
 * @author ASUS
 */
public class Home extends Form{
Form current;
private Resources theme;
    public Home(Form previous,Resources res) {
        current=this; //Back 
        add(new Label("Home"));
        setTitle(" --Home-- ");
        setLayout(BoxLayout.y());
        
    Button BUTAdd = new Button("Add Arena");
    Button BUTShow = new Button("Show Arena");   
    Button BUTAddC = new Button("Add Cours");
    Button BUTShowC = new Button("Show Cours");
    //Button BUTCam = new Button("Camera ");
   BUTAdd.addActionListener((evt) -> new AjoutArenaForm(current).show());
   BUTAddC.addActionListener((evt) -> new AjoutCoursForm(current).show());
    //BUTCam.addActionListener((evt) -> new Camera().show());
    BUTShow.addActionListener((evt) -> new ListArenaForm(current).show());
     BUTShowC.addActionListener((evt) -> new ListCoursForm(current).show());
       
    addAll(BUTAdd,BUTShow,BUTAddC,BUTShowC);
    
    getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, (evt) -> {
        previous.showBack();
        });}
    }
