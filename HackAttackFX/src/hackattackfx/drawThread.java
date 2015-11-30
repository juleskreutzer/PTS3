/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import javafx.scene.shape.Line;

/**
 *
 * @author Bart van Keersop
 */
public class drawThread implements Runnable{

    Defense defense;
    Minion minion;
    GraphicsEngine engine;
    
    public drawThread(GraphicsEngine engine, Defense defense, Minion minion){
        this.engine = engine;
        this.defense = defense;
        this.minion = minion;
    }
    @Override
    public void run() {
        Line lineBlue = new Line(defense.getPosition().x, defense.getPosition().y, minion.getPosition().x, minion.getPosition().y);
        
    }
    
}
