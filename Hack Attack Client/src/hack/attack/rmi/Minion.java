package hack.attack.rmi;

import hack.attack.client.templates.MinionTemplate;
import hack.attack.client.enums.MinionType;
import hack.attack.client.exceptions.UnsubscribeNonListenerException;
import hack.attack.rmi.IMoveable;
import hack.attack.rmi.ITargetable;
import java.awt.Point;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jules Kreutzer, Jasper Rouwhorst
 */
public class Minion implements ITargetable {

    public interface MinionHeartbeat{
    //TODO Check if (hp <= 0)
        void onMinionDeath(Minion minion, Boolean reachedBase);
    }
    
    private static final long serialVersionUID = 000002L;
    
    //Fields
    private MinionType minionType; //The MinionType of the minion
    private double health; //The ammunt of health the minion currently has.
    private double initialHealth;
    private double damage;
    private boolean reachedBase;
    private Point.Double position; //The current position of the minion.
    private double reward; //The ammount of bitcoins the minion is worth, the opposing player gains this upon the minions destruction.
    
    
    // Constructor
    /**
     * The constructor will get the data to create the minion instance from de DATA-class
     * according to the minionType
     * @param type is the MinionType of the minion than needs to be created
     * @param callback this callback triggers the {@link MinionHeartBeat.onMinionDeath(Minion minion)} when a minion dies
     */
    public Minion(MinionType type)
    {
        throw new UnsupportedOperationException("The minion class doesn't get it\'s data from the data-class yet.");
    }
    
    public double getHealthInPercentage() {
        return (this.health / this.initialHealth) * 100;
    }
    
    /**
     * Get the health of the minion
     * @return return the health of the minion as a double
     */
    public double getHealth()
    {
        return this.health;
    }
    
    /**
     * This method will change the health of the minion to the amount given
     * @param health the amount of health for the minion
     */
    public void setHealth(double health)
    {
        this.health = health;
    }
    
    /**
     * Get the x,y coordinate of a minion
     * @return returns an array with the x and y coordinate of the minion
     */
    public Point getPosition()
    {
        return new Point((int)position.x, (int)position.y);
    }
    
    public void setPosition(Point pos){
        this.position = new Point.Double(pos.x, pos.y);
    }
    
    /**
     * Get the reward value
     * @return reward value as double
     */
    public double getReward()
    {
        return this.reward;
    }
    
    public double getDamage()
    {
        return this.damage;
    }
    
    public boolean reachedBase()
    {
        return this.reachedBase;
    }
    
    public MinionType getMinionType(){
        return minionType;
    }
}
