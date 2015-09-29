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
 *
 * @author juleskreutzer
 */
public class Defense extends Module {
    private double damage;
    private int range;
    private DefenseType type;
    private Effect effect;
    
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
    
    public boolean upgrade(){
        return false;
    }
    
    public Minion findTarget(){
        return null;
    }
    
    public void fire(Minion minion){
        
    }
    
    
    
    
    
    
    
}
