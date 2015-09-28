package hack.attack;

import java.awt.Image;
import java.util.List;

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
    private List<Image> drawables;
    
    private GraphicsEngine(){
        instance = this;
    }
    
    public static GraphicsEngine getInstance(){
        return instance == null ? new GraphicsEngine() : instance;
    }
    
    public void spawn(Object object){
        
    }
    
    public double update(){
        return 0;
    }
    
    private void draw(){
        
    }
    
    public void drawSpellRange(Spell spell){
        
    }
    
    
    
}
