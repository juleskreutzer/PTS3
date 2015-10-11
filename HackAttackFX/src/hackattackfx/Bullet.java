/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import java.awt.Rectangle;
import java.awt.Point;
import hackattackfx.interfaces.IMoveable;
/**
 * This is the bullet that's fired by a defense module. It implemented IMovable so the bullet can check it's own collision with an entity.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Bullet implements IMoveable {
    
    private Point targetPosition; //The position of the target the bullet is moving towards.
    private double speed; //The speed of the bullet.
    private double damage; //The damage the bullet deals to a Minion.
    private Rectangle bounds; //The bounds of the bullet.
    private Point position; //The current position of the bullet.
    
    /**
     * 
     * @param target The enemy {@link Minion} target, cannot be null!
     * @param position The initial position the bullet is spawned.
     * @param speed The speed the bullet moves with, must be above 0.
     * @param damage The damage the bullet inflicts, must be above 0.
     */
    public Bullet(Point target, Point position, int speed, double damage){
        //TODO
        //Check if target != null
        //Check if speed > 0
        //Check if damage > 0
        
        this.targetPosition = target;
        this.speed = speed;
        this.damage = damage;
        this.position = position;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    /**
     * Sets the speed of the bullet.
     * @param speed , must be above 0.
     */
    public void setSpeed(double speed){
        if (speed > 0)
        {
            this.speed = speed;
        }
    }
    
    public double getDamage(){
        return damage;
    }
    
    /**
     * Sets the damage of the bullet.
     * @param damage, must be above 0.
     */
    public void setDamage(double damage){
        if (damage > 0)
        {
            this.damage = damage;
        }
    }
    
    public Rectangle getBounds(){
        return bounds;
    }
    
    public void setBounds(Rectangle bounds){
        this.bounds = bounds;
    }

    //TODO Think of mechanism where bullet hit minion dependant on minion's moving path.
    @Override
    public void move(double deltaTime) {
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
