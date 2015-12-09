/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack.client;

import hack.attack.rmi.Defense;
import hack.attack.rmi.Minion;
import hack.attack.rmi.Module;
import javafx.scene.image.Image;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class MinionImage extends ObjectImage {

    private Minion minion;
    private Rectangle healthBar;
    private Module module;
    private boolean hovered;
    
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
        
        // Initialize healthbar
        healthBar = new Rectangle();
        healthBar.setWidth((this.getImage().getWidth()/100)*minion.getHealthInPercentage());
        healthBar.setHeight(5);
        healthBar.setX(this.getX());
        healthBar.setY(this.getY() + this.getImage().getHeight());
        healthBar.setFill(Color.RED);
        
        if(module instanceof Defense){
            Defense defense = (Defense)module;
            this.setOnMouseEntered(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    
                    hovered = true;
                    GraphicsEngine.getInstance().drawModuleStats(module);
                }
            
            });
            this.setOnMouseExited(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    
                    hovered = false;
                    GraphicsEngine.getInstance().drawModuleStats(null);
                }
            });
            this.setOnMouseClicked(new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent event) {
                    //Highlight selected module
                    GraphicsEngine.getInstance().moduleClicked((Module)getReference());
                }
            });
        }
        
    }
    
    public Minion getMinion(){
        return minion;
    }
    
    public Rectangle getHealthBar(){
        return healthBar;
    }
    
}
