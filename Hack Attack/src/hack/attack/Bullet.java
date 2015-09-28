/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Rectangle;

/**
 *
 * @author juleskreutzer
 */
public class Bullet {
    
    private double speed;
    private double damage;
    private Rectangle bounds;
    
    public Bullet(int speed, double damage){
        this.speed = speed;
        this.damage = damage;
    }
    
    public double getSpeed(){
        return 0;
    }
    
    public void setSpeed(double speed){
        
    }
    
    public double getDamage(){
        return 0;
    }
    
    public void setDamage(double damage){
        
    }
    
    public Rectangle getBounds(){
        return null;
    }
    
    public void setBounds(Rectangle bounds){

    }
}
