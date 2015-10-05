package hack.attack;

import java.awt.Image;
import java.util.List;
import hack.attack.exceptions.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class GraphicsEngine {
    
    private static GraphicsEngine instance;
    
    private double updateTime;
    private List<ObjectImage> drawables;
    
    private GraphicsEngine(){
        instance = this;
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
