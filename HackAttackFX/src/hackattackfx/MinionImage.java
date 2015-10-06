/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.MinionType;
import java.awt.Graphics;
import javafx.scene.image.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import hackattackfx.exceptions.*;
import java.io.File;
import javafx.scene.image.ImageView;

/**
 *
 * @author juleskreutzer
 */
public class MinionImage extends ObjectImage {

    private Minion minion;
    
    public MinionImage(Minion m){
        super(m);
        minion = m;
        
        File minion = new File("src/hackattackfx/resources/Byte.png");
        Image image = new javafx.scene.image.Image(minion.toURI().toString());
        this.setImage(image);
        
        
    }
    
    public Minion getMinion(){
        return minion;
    }
    
}
