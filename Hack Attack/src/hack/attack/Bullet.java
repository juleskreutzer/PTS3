/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Rectangle;

/**
 *
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Bullet implements hack.attack.interfaces.IMoveable {
    
    private double speed;
    private double damage;
    private Rectangle bounds;
    
    public Bullet(int speed, double damage){
        this.speed = speed;
        this.damage = damage;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
    }
    
    public double getDamage(){
        return damage;
    }
    
    public void setDamage(double damage){
        this.damage = damage;
    }
    
    public Rectangle getBounds(){
        return bounds;
    }
    
    public void setBounds(Rectangle bounds){
        this.bounds = bounds;
    }

    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getCollision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
