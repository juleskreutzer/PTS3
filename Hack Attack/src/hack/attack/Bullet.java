/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import java.awt.Rectangle;
import java.awt.Point;
/**
 * This is the bullet that's fired by a defense module. It implemented IMovable so the bullet can check it's own collision with an entity.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Bullet implements hack.attack.interfaces.IMoveable {
    
    private Point targetPosition;
    private double speed;
    private double damage;
    private Rectangle bounds;
    private Point position;
    
    /**
     * 
     * @param target The enemy {@link Minion} target.
     * @param position The initial position the bullet is spawned.
     * @param speed The speed the bullet moves with.
     * @param damage The damage the bullet inflicts.
     */
    public Bullet(Point target, Point position, int speed, double damage){
        this.targetPosition = target;
        this.speed = speed;
        this.damage = damage;
        this.position = position;
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
    public void move(int deltaTime) {
        int x1 = 10, y1 = 60;
        int x2 = 230, y2 = 400;
        /* Not implemented correctly yet
           double x = position.x + (position.x - targetPosition.x) * deltaTime * speed;
           Point newpos = 
           double y = lerp(y1, y2, t / 50.0);
           System.out.println("t = " + t + ", " + x + ", " + y);*/
    
    }

    @Override
    public Object getCollision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
