/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import hackattackfx.exceptions.*;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GraphicsEngine implements Initializable {
    
    @FXML
    private AnchorPane window;
    
    private static GraphicsEngine instance;
    
    private double updateTime;
    private List<ObjectImage> drawables;
    
    private GraphicsEngine(){
        instance = this;
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("User dir: "+System.getProperty("user.dir"));
        File minion = new File("Byte.png");
        Image image = new Image(minion.toURI().toString());
        ImageView iv = new ImageView();
        iv.setX(100);
        iv.setY(100);
        iv.setImage(image);
        System.out.println("Window childer: "+window.getChildren().toString());
        window.getChildren().add(iv);
    }    
    
    /**
     * From the moment an object is spawned, it will be updated every tick.
     * This is the entry point for an object to be updated every tick.
     * @param object 
     */
    public void spawn(Object object) throws DuplicateSpawnException{
        for(ObjectImage image : drawables){
            if(image.getReference() == object){
                throw new DuplicateSpawnException("This object already exists!");
            }
        }
        
        if(object instanceof Bullet){
            drawables.add(new BulletImage((Bullet)object));
        }else if(object instanceof Minion){
            drawables.add(new MinionImage((Minion)object));
        }else if(object instanceof Module){
            drawables.add(new ModuleImage((Module)object));
        }else if(object instanceof Spell){
            drawables.add(new SpellImage((Spell)object));
        }
    }
    
    public double update(){
        return 0;
    }
    
    private void draw(){
        /**
         * TODO implement this method using JavaFX.
         */
    }
    
    public void drawSpellRange(Spell spell){
        
    }
    
}
