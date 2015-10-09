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
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class MinionImage extends ObjectImage {

    private Minion minion;
    
    public MinionImage(Minion m){
        super(m);
        minion = m;
        
        File file = null;
        switch(minion.getMinionType()){
            case b:
                file = new File("src/hackattackfx/resources/Byte.png");
                break;
            case kb:
                file = new File("src/hackattackfx/resources/KiloByte.png");
                break;
            case mb:
                file = new File("src/hackattackfx/resources/MegaByte.png");
                break;
            case gb:
                file = new File("src/hackattackfx/resources/GigaByte.png");
                break;
            case tb: 
                file = new File("src/hackattackfx/resources/TeraByte.png");
                break;
            case pb:
                file = new File("src/hackattackfx/resources/PetaByte.png");
                break;
        }
        
        Image image = new javafx.scene.image.Image(file.toURI().toString());
        setX(minion.getPosition().x);
        setY(minion.getPosition().y);
        this.setImage(image);
    }
    
    public Minion getMinion(){
        return minion;
    }
    
}
