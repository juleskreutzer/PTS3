package hack.attack;

import hack.attack.enums.MinionType;
import hack.attack.interfaces.IMoveable;
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
public class Minion implements IMoveable{

    public interface MinionHeartbeat{
        void onMinionDeath(Minion minion);
    }
    
    //Fields
    private MinionType minionType;
    private Player enemyPlayer;
    private double health;
    private double speed;
    private Point position;
    // The position this minion is moving to
    private Point targetPosition;
    private double damage;
    private boolean encrypted;
    private double reward;
    
    private MinionHeartbeat heartbeat;
    
    // Constructor
    /**
     * The constructor will get the data to create the minion instance from de DATA-class
     * according to the minionType
     * @param type is the MinionType of the minion than needs to be created
     * @param callback this callback triggers the {@link MinionHeartBeat.onMinionDeath(Minion minion)} when a minion dies
     */
    public Minion(MinionType type, MinionHeartbeat callback)
    {
        switch(type){
            case b:
                health = Data.DEFAULT_BYTE.getHealth();
                speed = Data.DEFAULT_BYTE.getSpeed();
                minionType = Data.DEFAULT_BYTE.getMinionType();
                damage = Data.DEFAULT_BYTE.getDamage();
                encrypted = Data.DEFAULT_BYTE.getEncrypted();
                reward = Data.DEFAULT_BYTE.getReward();
                break;
            case kb:
                health = Data.DEFAULT_KILOBYTE.getHealth();
                speed = Data.DEFAULT_KILOBYTE.getSpeed();
                minionType = Data.DEFAULT_KILOBYTE.getMinionType();
                damage = Data.DEFAULT_KILOBYTE.getDamage();
                encrypted = Data.DEFAULT_KILOBYTE.getEncrypted();
                reward = Data.DEFAULT_KILOBYTE.getReward();
                break;
            case mb:
                health = Data.DEFAULT_MEGABYTE.getHealth();
                speed = Data.DEFAULT_MEGABYTE.getSpeed();
                minionType = Data.DEFAULT_MEGABYTE.getMinionType();
                damage = Data.DEFAULT_MEGABYTE.getDamage();
                encrypted = Data.DEFAULT_MEGABYTE.getEncrypted();
                reward = Data.DEFAULT_MEGABYTE.getReward();
                break;
            case gb:
                health = Data.DEFAULT_GIGABYTE.getHealth();
                speed = Data.DEFAULT_GIGABYTE.getSpeed();
                minionType = Data.DEFAULT_GIGABYTE.getMinionType();
                damage = Data.DEFAULT_GIGABYTE.getDamage();
                encrypted = Data.DEFAULT_GIGABYTE.getEncrypted();
                reward = Data.DEFAULT_GIGABYTE.getReward();
                break;
            case tb:
                health = Data.DEFAULT_TERABYTE.getHealth();
                speed = Data.DEFAULT_TERABYTE.getSpeed();
                minionType = Data.DEFAULT_TERABYTE.getMinionType();
                damage = Data.DEFAULT_TERABYTE.getDamage();
                encrypted = Data.DEFAULT_TERABYTE.getEncrypted();
                reward = Data.DEFAULT_TERABYTE.getReward();
                break;
        }
        
        heartbeat = callback;
        GameEngine.getInstance().setOnTickListener(new GameEngine.OnExecuteTick() {

            @Override
            public void onTick(double delta) {
                move(delta);
            }
        });
    }
    
    public Minion(MinionTemplate minion, MinionHeartbeat callback)
    {
        health = minion.getHealth();
        speed = minion.getSpeed();
        minionType = minion.getMinionType();
        damage = minion.getDamage();
        encrypted = minion.getEncrypted();
        reward = minion.getReward();
        heartbeat = callback;
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
    
    public double getDamage(){
        return damage;
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
    
    public MinionType getMinionType(){
        return minionType;
    }
    
    @Override
    public void move(double delta) {
        if(position.x > targetPosition.x){
            // Correct position to prevent the minion to move further then it's target
            if(position.x - (speed * delta) < targetPosition.x){
                position.x = targetPosition.x;
            }else{
                position.x -= (speed * delta);
            }
        }else if(position.x < targetPosition.x){
            if(position.x + (speed * delta) > targetPosition.x){
                position.x = targetPosition.x;
            }else{
                position.x += (speed * delta);
            }
        }
        
        if(position.y > targetPosition.y){
            if(position.y - (speed * delta) > targetPosition.y){
                position.y = targetPosition.y;
            }else{
                position.y -= (speed * delta);
            }
        }else if(position.y < targetPosition.y){
            if(position.y + (speed * delta) < targetPosition.y){
                position.y = targetPosition.y;
            }else{
                position.y += (speed * delta);
            }
        }
    }

    @Override
    public Object getCollision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
