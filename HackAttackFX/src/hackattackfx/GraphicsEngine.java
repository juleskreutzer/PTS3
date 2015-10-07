/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.util.List;
import hackattackfx.exceptions.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Jasper Rouwhorst
 */
public class GraphicsEngine{
    
    private FXMLDocumentController parent;
    private static GraphicsEngine instance;
    
    private double updateTime;
    
    private GraphicsEngine(){
        instance = this;
        parent = FXMLDocumentController.getInstance();
        
        initialize();
        
    }
    
    private void initialize(){
        
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    /**
     * From the moment an object is spawned, it will be updated every tick.
     * This is the entry point for an object to be updated every tick.
     * @param object 
     */
    public void spawn(Object object) throws DuplicateSpawnException{
        // Checks if the object doesn't exists already
        List<ObjectImage> list = (List<ObjectImage>)parent.getAllNodes();
        for(ObjectImage image : list){
            if(image.getReference() == object){
                throw new DuplicateSpawnException("This object already spawned!");
            }
        }
        
        if(object instanceof Bullet){
            parent.addNode(new BulletImage((Bullet)object));
        }else if(object instanceof Minion){
            parent.addNode(new MinionImage((Minion)object));
        }else if(object instanceof Module){
            parent.addNode(new ModuleImage((Module)object));
        }else if(object instanceof Spell){
            parent.addNode(new SpellImage((Spell)object));
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
    
    public void drawRoad(Road road){
        for(Path p : road.disect()){
            Line line = new Line(p.getStart().x, p.getStart().y, p.getEnd().x, p.getEnd().y);
            line.setStroke(Color.BLACK);
            parent.addNode(line);
        }
    }
    
    public void drawSpellRange(Spell spell){
        
    }
    
}
