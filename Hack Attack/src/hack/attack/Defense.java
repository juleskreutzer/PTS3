/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hack.attack;

import hack.attack.enums.DefenseType;
import hack.attack.enums.Effect;
import hack.attack.enums.ModuleName;
import java.awt.Point;
import hack.attack.exceptions.*;

/**
 * This is a module used for the defense of your base. 
 * It fires {@link Bullet } at enemy minions.
 * @author juleskreutzer, Jasper Rouwhorst
 */
public class Defense extends Module {
    private double damage;
    private int range;
    private DefenseType type;
    private Effect effect;
    
    /**
     * 
     * @param cost The costs to build this module(cannot be < 1).
     * @param position The position this module is build(upleft corner).
     * @param width The width of this module.
     * @param height The height of this module.
     * @param name The {@link ModuleName} of this module.
     * @param type The {@link DefenceType} of this module. 
     * @param effect The {@link Effect} this module adds to its target.
     * @param level The initial level of this module(cannot be < 1).
     * @param damage The damage this module inflicts to its target.
     * @param range The range this module can find targets in(cannot be < 1).
     */
    public Defense(double cost, Point position, int width, int height, ModuleName name, DefenseType type, Effect effect, int level, double damage, int range)
    {
        super(cost, position, width, height, name, level);
        
        this.type = type;
        this.effect = effect;
        this.damage = damage;
        this.range = range;
    }
    
    /**
     * 
     * @return Type of the object as String
     */
    public String getDefenceTypeString()
    {
        String result;
        
        switch(type)
        {
            case range:
                result = "range";
                break;
            case cheap:
                result = "cheap";
                break;
            case balanced:
                result = "balanced";
                break;
            case strong:
                result = "strong";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    /**
     * 
     * @return return the effect as String
     */
    public String getEffectString()
    {
        String result;
        
        switch(effect)
        {
            case slowed:
                result = "slow";
                break;
            case poisoned:
                result = "poison";
                break;
            case splash:
                result = "slash";
                break;
            case decrypted:
                result = "decryptor";
                break;
            default:
                result = "";
        }
        return result;
    }
    
    public DefenseType getDefenceType(){
        return type;
    }
    
    public Effect getEffect(){
        return effect;
    }
    
    public void setEffect(Effect e){
        effect = e;
    }
    
    public double getDamage(){
        return damage;
    }
    
    public void setDamage(double d){
        damage = d;
    }
    
    public int getRange(){
        return range;
    }
    
    public void setRange(int r){
        range = r;
    }
    
    /**
     * Upgrades this module
     * @return Whether the module was successfully upgraded.
     */
    public boolean upgrade(){
        level++;
        damage = level * 10;
        return true;
    }
    
    /**
     * Look for enemy minions within the range of this module, then randomly select a target.
     * @return The enemy minion that's targeted.
     */
    public Minion findTarget(){
        return null;
    }
    
    /**
     * Fire a {@link Bullet} at the module's target.
     * @param minion The enemy minion target.
     */
    public void fire(Minion minion){
        
    }
    
}
