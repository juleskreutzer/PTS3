package hack.attack.rmi;

import hack.attack.client.templates.MinionTemplate;
import java.awt.Point;

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
    private long minionID;
    
    //Fields
    private MinionType minionType; //The MinionType of the minion
    private double health; //The ammunt of health the minion currently has.
    private double initialHealth; // the health this minion had when created.
    private double speed; //The rate at which the minion moves towards the targetPosition.
    private Point.Double position; //The current position of the minion.
    private double damage; //The damage the minion will deal upon reching enemyPlayer.
    private boolean reachedBase; // whether the minion reached enemy base
    private int ownerID;
    private static long nextMinionID; // Unique id of a minion
    
    private Point targetPosition; // The position this minion is currently moving to. Can change.
    private boolean encrypted; //Is true when the minion is encrypted.
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
    
    /**
     * The minion, loaded from MinionTemplate with some modified stats.
     * @param minion, the template of minion that is loaded from the database.
     * @param multiplier, the multiplier that is used to increase certain values.
     */
    public Minion(MinionTemplate minion, double multiplier, int ownerID)
    {
        minionID = nextMinionID++;
        health = (minion.getHealth() * multiplier);
        initialHealth = health;
        speed = (minion.getSpeed());
        damage = (minion.getDamage());
        reward = (minion.getReward());
        encrypted = minion.getEncrypted();
        minionType = minion.getMinionType();
        this.ownerID = ownerID;
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
    
    public int getOwnerID()
    {
        return this.ownerID;
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
    
    public long getMinionID()
    {
        return this.minionID;
    }
}
