package hack.attack;

import hack.attack.enums.MinionType;
import java.awt.Point;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jules Kreutzer
 */
public class Minion {
    
    public interface MinionHeartbeat{
        void onMinionDeath(Minion minion);
    }
    
    //Fields
    private MinionType minionType;
    private double health;
    private double speed;
    private Point[] position;
    private Point[] targetPosition;
    private double damage;
    private boolean encrypted;
    private double reward;
    
    // Constructor
    /**
     * The constructor will get the data to create the minion instance from de DATA-class
     * according to the minionType
     * @param type is the MinionType of the minion than needs to be created
     */
    public Minion(MinionType type)
    {
        throw new UnsupportedOperationException("The minion class doesn't get it\'s data from the data-class yet.");
    }
    
    public Minion(MinionHeartbeat listener)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
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
    public Point[] getPosition()
    {
        return this.position;
    }
    
    /**
     * Get the speed of the minion
     * @return the speed of the minion
     */
    public double getSpeed()
    {
        return this.speed;
    }
    
    /**
     * Set the speed of the minion to the amount given
     * @param speed the speed for the minion
     */
    public void setSpeed(double speed)
    {
        this.speed = speed;
    }
    
    /**
     * Ask if the minion is encrypted or not
     * @return True if encrypted, false if not encrypted
     */
    public boolean getEncrypted()
    {
        return this.encrypted;
    }
    
    /**
     * Change if a minion is encrypted or not
     * @param encrypted boolean if minion is encrypted
     */
    public void setEncrypted(boolean encrypted)
    {
        this.encrypted = encrypted;
    }
    
    /**
     * Get the reward value
     * @return reward value as double
     */
    public double getReward()
    {
        return this.reward;
    }
    
    /**
     * Attack the opposite player 
     * @param player the object of the player you want to attack
     */
    public void attack(Player player)
    {
        player.receiveDamage(this.damage);
    }
    
    /**
     * Get the next position the minion should go to
     */
    private void getNextTarget()
    {
        
    }
    
}
