package hackattackfx;

import hackattackfx.GameEngine.OnExecuteTick;
import hackattackfx.MinionEffect.OnEffectExpired;

import hackattackfx.enums.Effect;
import hackattackfx.templates.MinionTemplate;
import hackattackfx.enums.MinionType;
import hackattackfx.exceptions.UnsubscribeNonListenerException;
import hackattackfx.interfaces.IMoveable;
import hackattackfx.interfaces.ITargetable;
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
public class Minion implements IMoveable, ITargetable {

    public interface MinionHeartbeat{
    //TODO Check if (hp <= 0)
        void onMinionDeath(Minion minion, Boolean reachedBase);
    }
    
    //Fields
    private MinionType minionType; //The MinionType of the minion
    private Player enemyPlayer; //The player the minions are supposed to attack.
    private double health; //The ammunt of health the minion currently has.
    private double initialHealth; // the health this minion had when created.
    private double speed; //The rate at which the minion moves towards the targetPosition.
    private Point.Double position; //The current position of the minion.
    private double damage; //The damage the minion will deal upon reching enemyPlayer.
    private boolean reachedBase; // whether the minion reached enemy base
    
    private Point targetPosition; // The position this minion is currently moving to. Can change.
    private boolean encrypted; //Is true when the minion is encrypted.
    private double reward; //The ammount of bitcoins the minion is worth, the opposing player gains this upon the minions destruction.
    
    private OnExecuteTick tickListener;
    private MinionHeartbeat heartbeat;
    private MinionEffect activeEffect;
    
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
    public Minion(MinionTemplate minion, double multiplier, Player enemyPlayer)
    {
        health = (minion.getHealth() * multiplier);
        initialHealth = health;
        speed = (minion.getSpeed());
        damage = (minion.getDamage());
        reward = (minion.getReward());
        encrypted = minion.getEncrypted();
        minionType = minion.getMinionType();
        this.enemyPlayer = enemyPlayer;
    }
    
    public double getHealthInPercentage() {
        return (this.health / this.initialHealth) * 100;
    }
    
    // The minion will response to ticks from now on
    public void activate(MinionHeartbeat callback){
        this.heartbeat = callback;
        tickListener = new OnExecuteTick(){
            @Override
            public void onTick(long elapsedtime) {
                move(elapsedtime);
            }
            
        };
        GameEngine.getInstance().setOnTickListener(tickListener);
    }
    
    public void deactivate(){
        try {
            GameEngine.getInstance().unsubscribeListener(tickListener);
        } catch (UnsubscribeNonListenerException ex) {
            Logger.getLogger(Minion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //TODO Doesn't Use elapsedTime yet.
    @Override
    public void move(double elapsedtime) {
        if(targetPosition == null){
            targetPosition = Map.getInstance().getRoad().getBegin();
        }
        
        if(position.x < targetPosition.x){
            position.x += (speed);
            // Correct the position if the minions new position is over the targetposition
            if(position.x >= targetPosition.x){
                position.x = targetPosition.x;
            }
        }
        else if(position.x > targetPosition.x){
            position.x -= (speed);
            // Correct the position if the minions new position is over the targetposition
            if(position.x <= targetPosition.x){
                position.x = targetPosition.x;
            }
        }
        else if(position.y < targetPosition.y){
            position.y += (speed);
            // Correct the position if the minions new position is over the targetposition
            if(position.y >= targetPosition.y){
                position.y = targetPosition.y;
            }
        }
        else if(position.y > targetPosition.y){
            position.y -= (speed);
            // Correct the position if the minions new position is over the targetposition
            if(position.y <= targetPosition.y){
                position.y = targetPosition.y;
            }
        }
        else{
            List<Path> paths = Map.getInstance().getRoad().getPaths();
            for(Path p : paths){
                if(targetPosition.x == p.getStart().x && targetPosition.y == p.getStart().y){
                    targetPosition = p.getEnd();
                    break;
                }else if(targetPosition.x == p.getEnd().x && targetPosition.y == p.getEnd().y){
                    if(paths.indexOf(p) != paths.size() -1){
                        targetPosition = paths.get(paths.indexOf(p)+1).getEnd();
                    }
                    break;
                }
            }
            if (position.x == targetPosition.x && position.y == targetPosition.y) {
                // minion has reached the end of the path
                this.health = 0;
                reachedBase = true;
                heartbeat.onMinionDeath(this, reachedBase);
            }
        }
        
    }
    
    public boolean reachedBase()
    {
        return reachedBase;
    }

    @Override
    public Object getCollision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    public void setDamage(double damage){
        this.damage = damage;
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
    
    public void setReward(double reward){
        this.reward = reward;
    }
    
    /**
     * Attack the opposite player 
     * @param player the object of the player you want to attack
     */
    public void attack(Player player)
    {
        player.receiveDamage(this.damage);
    }
    
    public MinionType getMinionType(){
        return minionType;
    }
    
    public void receiveDamage(double damage){
        health -= damage;
        //System.out.println(String.format("Dealing damage: %f, remaining health: %f", damage, health));
        if(health <= 0){
            if (heartbeat != null) {
                //System.out.println();
                heartbeat.onMinionDeath(this, false);
            }
        }
    }
    
    public void applyEffect(MinionEffect effect){
        activeEffect = effect.getEffectType() != Effect.DIE && effect.getEffectType() != Effect.REACHED_BASE ? effect : null;
        switch(effect.getEffectType()){
            case SLOWED:
                speed /= 2;
                break;
            case POISENED:
                
                break;
            case SPLASH:
                
                break;
            case DECRYPTED: 
                
                break;
            case DIE:
                
                break;
            case REACHED_BASE:
                
                break;
        }
    }
    
    public void removeEffect(){
        switch(activeEffect.getEffectType()){
            case SLOWED:
                speed *= 2;
                break;
            case POISENED:
                
                break;
            case SPLASH:
                
                break;
            case DECRYPTED: 
                
                break;
            case DIE:
                
                break;
            case REACHED_BASE:
                
                break;
        }
        activeEffect = null;
    }
    
}
