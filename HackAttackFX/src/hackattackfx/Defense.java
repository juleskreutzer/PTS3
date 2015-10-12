/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackattackfx;

import hackattackfx.enums.DefenseType;
import hackattackfx.enums.Effect;
import hackattackfx.enums.ModuleName;
import java.awt.Point;
import hackattackfx.exceptions.*;
import hackattackfx.templates.*;
import java.util.Arrays;

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
     * @param cost The costs to build this module(must be > 0).
     * @param position The position this module should be builded(upleft corner), must fall within the X and Y values of the singleton class Map.
     * @param width The width of this module.
     * @param height The height of this module.
     * @param name The {@link ModuleName} of this module.
     * @param type The {@link DefenceType} of this module, cannot be null.
     * @param effect The {@link Effect} this module adds to its target, can be null.
     * @param level The initial level of this module(must be > 0).
     * @param damage The damage this module inflicts to its target. (must be > 0).
     * @param range The range this module can find targets in(must be > 0).
     */
    public Defense(double cost, Point position, int width, int height, ModuleName name, DefenseType type, Effect effect, int level, double damage, int range)
    {
        //TO DO
        //Check if cost > 0.
        //Check if position is within map bounds.
        //Check if level > 0.
        //Check if damage > 0.
        //Check if range > 0.
        
        super(cost, position, width, height, name, level);
        
        this.type = type;
        this.effect = effect;
        this.damage = damage;
        this.range = range;
    }
    
    /**
     * Constructor for the Defense module based on the DefenseTemplate
     * @param template instance of DefenseTemplate created in the data class
     * @param position position of the module on the map
     * @param width width of the module
     * @param height height of the module
     * 
     * Because we have to check if the given ModuleName is correct, we create an array of ModuleName with all the possible values and check if the given ModuleName is correct
     */
    public Defense(DefenseTemplate template, Point position, int width, int height)
    {
        super(template.getCost(), position, width, height, template.getModuleName(), template.getLevel());
        ModuleName ModuleNameList[] = new ModuleName[] {ModuleName.BOTTLECAP_ANTIVIRUS, ModuleName.MUSCLE_ANTIVIRUS, ModuleName.SCALE_ANTIVIRUS, ModuleName.SNIPER_ANTIVIRUS};
        if(!Arrays.asList(ModuleNameList).contains(template.getModuleName()))
        {
            throw new InvalidModuleEnumException();
        }
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
    
    /**
     * Sets the Defense modules effect.
     * @param e
     */
    public void setEffect(Effect e){
        effect = e;
    }
    
    public double getDamage(){
        return damage;
    }
    
    /**
     * Sets the towers damage.
     * @param d, must be above 0.
     */
    public void setDamage(double d){
        if (d > 0)
        {
            damage = d;
        }
    }
    
    public int getRange(){
        return range;
    }
    
    /**
     * Sets the towers range.
     * @param r, must be above 0.
     */
    public void setRange(int r){
        if (range > 0)
        {
            range = r;
        }
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
